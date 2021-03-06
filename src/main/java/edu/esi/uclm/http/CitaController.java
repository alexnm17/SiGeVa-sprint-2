package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.CupoDao;
import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Cupo;
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.esi.uclm.model.Usuario;

@RestController
public class CitaController {

	@Autowired
	private FormatoVacunacionDao formatoVacunacionDao;
	@Autowired
	private CitaDao citaDao;
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;
	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private CupoDao cupoDao;

	private static final String EMAIL_USUARIO = "emailUsuario";

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/solicitarCita")
	public void solicitarCita(HttpSession session, @RequestBody Map<String, Object> info) {
		JSONObject json = new JSONObject(info);
		String email = json.getString("email");
		try {
			Usuario usuario = usuarioDao.findByEmail(email);

			if (usuario == null)
				throw new SigevaException(HttpStatus.NOT_FOUND, "No se ha encontrado ningun usuario con este email");
			if (usuario.getEstadoVacunacion().equals(EstadoVacunacion.VACUNADO_SEGUNDA.name()))
				throw new SigevaException(HttpStatus.CONFLICT, "El usuario con DNI: " + usuario.getDni()
				+ " ya ha sido vacunado de las dos dosis." + " No puede volver a solicitar cita");

			int citasAsignadas = citaDao.findAllByUsuario(usuario).size();

			switch (citasAsignadas) {
			case 0:
				// Primera
				asignarDosis(usuario, LocalDate.now());
				// Segunda
				asignarDosis(usuario, LocalDate.now().plusDays(21));
				break;

			case 2:
				throw new SigevaException(HttpStatus.FORBIDDEN, "El paciente: " + usuario.getDni()
				+ " ya dispone de dos citas asignadas. Si desea modificar su cita, utilice Modificar Cita");

			case 1:
				Cita primeraDosis = citaDao.findByUsuario(usuario);
				LocalDate fechaPrimeraCita = LocalDate.parse(primeraDosis.getFecha());

				// Asignar SegundaDosis
				asignarDosis(usuario, fechaPrimeraCita.plusDays(21));
				break;

			default:
				break;
			}

		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	private Cupo buscarCupoLibre(LocalDate fechaActualDate, CentroVacunacion centroVacunacion) {
		Cupo cupo = null;

		// Para poder coger siempre la primera con un hueco libre por fecha
		List<Cupo> listaCupos = cupoDao.findAllByCentroVacunacion(centroVacunacion);
		listaCupos.sort(Comparator.comparing(Cupo::getFecha));

		for (int i = 0; i < listaCupos.size(); i++) {
			cupo = listaCupos.get(i);
			if (LocalDate.parse(cupo.getFecha()).isAfter(fechaActualDate) && cupo.getPersonasRestantes() > 0) {
				break;
			}
		}
		return cupo;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/modificarCita")
	public void modificarCita(@RequestBody Map<String, Object> datosCita) {

		try {

			JSONObject json = new JSONObject(datosCita);
			String idCita = json.getString("idCita");
			String idCupo = json.getString("idCupo");
			String emailUsuario = json.getString(EMAIL_USUARIO);

			Usuario usuario = usuarioDao.findByEmail(emailUsuario);
			Cita citaModificar = citaDao.findByIdCita(idCita);
			Optional<Cupo> optCupoElegido = cupoDao.findById(idCupo);
			Cupo cupoElegido = new Cupo();

			if (optCupoElegido.isPresent())
				cupoElegido = optCupoElegido.get();

			List<Cita> listaCitas = citaDao.findAllByUsuarioEmail(usuario.getEmail());
			listaCitas.sort(Comparator.comparing(Cita::getFecha));
			int citasAsignadas = listaCitas.size();

			if (citasAsignadas < 1)
				throw new SigevaException(HttpStatus.NOT_FOUND,
						"No se puede modificar citas puesto que no dispone de ninguna cita asignada");

			if (cupoElegido.getPersonasRestantes() < 1)
				throw new SigevaException(HttpStatus.FORBIDDEN,
						"No hay hueco para cita el dia " + cupoElegido.getFecha() + " a las " + cupoElegido.getHora());

			if (citaModificar.getIsUsada())
				throw new SigevaException(HttpStatus.NOT_FOUND,
						"No se puede modificar su cita puesto que ya est?? vacunado");

			if(listaCitas.size()==2) {
				int indiceCita = -1;
				if(citaModificar.getIdCita().equalsIgnoreCase(listaCitas.get(0).getIdCita()))
					indiceCita = 0;
				else if(citaModificar.getIdCita().equalsIgnoreCase(listaCitas.get(1).getIdCita()))
					indiceCita = 1;

				switch (indiceCita) {
				case 0:
					if (LocalDate.parse(cupoElegido.getFecha()).isAfter(LocalDate.parse(listaCitas.get(1).getFecha()))
							|| LocalDate.parse(cupoElegido.getFecha())
							.isEqual(LocalDate.parse(listaCitas.get(1).getFecha())))
						throw new SigevaException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,
								"No se puede poner la primera cita el mismo dia o un dia posterior a la primera");
					break;
				case 1:
					if (LocalDate.parse(cupoElegido.getFecha())
							.isBefore(LocalDate.parse(listaCitas.get(0).getFecha()).plusDays(21)))
						throw new SigevaException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,
								"No se puede poner la primera cita el mismo dia o un dia posterior a la primera");
					break;
				default:
					break;
				}
			}

			citaModificar.setFecha(cupoElegido.getFecha());
			citaModificar.setHora(cupoElegido.getHora());
			citaDao.save(citaModificar);

			cupoElegido.setPersonasRestantes(cupoElegido.getPersonasRestantes() - 1);
			cupoDao.save(cupoElegido);

		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/anularCita")
	public void anularCita(HttpServletRequest request, @RequestBody Map<String, Object> info) {
		try {
			JSONObject json = new JSONObject(info);
			String idCita = json.getString("idCita");
			Cita cita = citaDao.findByIdCita(idCita);

			if (cita.getIsUsada())
				throw new SigevaException(HttpStatus.CONFLICT, "La cita que intenta anular ya ha sido utilizada.");

			Cupo cupo = cupoDao.findAllByCentroVacunacionAndFechaAndHora(cita.getCentroVacunacion(), cita.getFecha(),
					cita.getHora());
			cupo.setPersonasRestantes(cupo.getPersonasRestantes() + 1);

			citaDao.deleteByIdCita(idCita);
			cupoDao.save(cupo);
		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@GetMapping("/consultarCita")
	public List<Cita> consultar(HttpSession session) {
		try {
			Usuario usuario = usuarioDao.findByEmail((String) session.getAttribute(EMAIL_USUARIO));
			List<Cita> citas = citaDao.findAllByUsuario(usuario);
			if (citas.isEmpty())
				throw new SigevaException(HttpStatus.NOT_FOUND,
						"No se ha podido encontrar ninguna cita para el usuario. Contacte con el administrador.");

			return citas;

		} catch (SigevaException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping("/crearCupo")
	public void crearCupo(int numFranjas, LocalDate fecha, LocalTime horaInicio, CentroVacunacion centroVacunacion,
			int duracion, int personasMax) {
		Cupo cupo;

		for (int i = 0; i < numFranjas; i++) {

			cupo = new Cupo(fecha.toString(), horaInicio.toString(), centroVacunacion, personasMax);
			cupoDao.save(cupo);

			horaInicio = horaInicio.plusMinutes(duracion);

		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/crearPlantillasCitaVacunacion")
	public void crearPlantillasCitaVacunacion() {
		FormatoVacunacion formato = getFormatoVacunacion();
		List<CentroVacunacion> centrosVacunacion = centroVacunacionDao.findAll();

		int horaFin = LocalTime.parse(formato.getHoraFinVacunacion()).getHour();
		int horaInicio = LocalTime.parse(formato.getHoraInicioVacunacion()).getHour();
		double duracion = (double) formato.getDuracionFranjaVacunacion() / 60;
		int numFranjas = (int) ((horaFin - horaInicio) / duracion);

		for (int i = 0; i < centrosVacunacion.size(); i++) {
			LocalDate fechaCita = LocalDate.now();

			while (fechaCita.isBefore(LocalDate.parse(LocalDate.now().plusYears(1).getYear() + "-02-01"))) {

				crearCupo(numFranjas, fechaCita, LocalTime.parse(formato.getHoraInicioVacunacion()),
						centrosVacunacion.get(i), formato.getDuracionFranjaVacunacion(),
						formato.getPersonasPorFranja());

				fechaCita = fechaCita.plusDays(1);
			}
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getFormatoVacunacion")
	public FormatoVacunacion getFormatoVacunacion() {
		Optional<FormatoVacunacion> optFormato = formatoVacunacionDao.findById("Formato_Unico");
		FormatoVacunacion formatoVacunacion = null;
		if (optFormato.isPresent())
			formatoVacunacion = optFormato.get();
		else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe un formato de Vacunacion definido");
		}
		return formatoVacunacion;
	}

	public void asignarDosis(Usuario usuario, LocalDate fechaActual) throws SigevaException {
		Cupo cupoAsignado = buscarCupoLibre(fechaActual, usuario.getCentroVacunacion());
		if (cupoAsignado == null)
			throw new SigevaException(HttpStatus.NOT_FOUND,
					"No se ha podido encontrar un cupo disponible en estos momentos");
		cupoDao.deleteByFechaAndHoraAndCentroVacunacion(cupoAsignado.getFecha(), cupoAsignado.getHora(),
				cupoAsignado.getCentroVacunacion());
		cupoAsignado.setPersonasRestantes(cupoAsignado.getPersonasRestantes() - 1);
		cupoDao.save(cupoAsignado);

		Cita cita = new Cita(cupoAsignado.getFecha(), cupoAsignado.getHora(), usuario);
		citaDao.save(cita);

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/getCitasHoy")
	public List<Cita> getCitasHoy(@RequestBody Map<String, Object> info) {
		JSONObject json = new JSONObject(info);
		String email = json.getString(EMAIL_USUARIO);
		String fecha = LocalDate.now().toString();
		return getCitasPorDia(fecha, email);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/getCitasOtroDia")
	public List<Cita> getCitasOtroDia(HttpSession session, @RequestBody Map<String, Object> info) {

		JSONObject json = new JSONObject(info);
		String fecha = json.getString("fecha");
		String email = json.getString(EMAIL_USUARIO);

		return getCitasPorDia(fecha, email);
	}

	public List<Cita> getCitasPorDia(String fecha, String email) {
		return citaDao.findAllByFechaAndCentroVacunacion(fecha, usuarioDao.findByEmail(email).getCentroVacunacion());
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getCitaByEmail")
	public List<Cita> getCitaByEmail(HttpServletRequest request, @RequestParam String email) {
		return citaDao.findAllByUsuarioEmail(email);
	}

}

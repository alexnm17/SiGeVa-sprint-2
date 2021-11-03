package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.CupoDao;
import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Cupo;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.esi.uclm.model.Usuario;
import edu.uclm.esi.exceptions.SiGeVaException;

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

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/solicitarCita")
	public void solicitarCita(HttpSession session, @RequestBody Map<String, Object> datosUsuario) {

		try {
			JSONObject json = new JSONObject(datosUsuario);
			String dni = json.getString("dni");

			Usuario usuario = usuarioDao.findByDni(dni);

			if (usuario == null)
				throw new SiGeVaException(HttpStatus.NOT_FOUND, "No se ha encontrado ningun usuario con este dni");

			CentroVacunacion centroVacunacion = centroVacunacionDao.findByNombre(usuario.getCentroSalud());

			LocalDate fechaActualDate = LocalDate.now();

			List<Cupo> listaCupos = cupoDao.findAllByCentroVacunacion(centroVacunacion);
			// Posible throw de exception

			Cupo primerCupo = buscarCupoLibre(fechaActualDate, listaCupos);

			if (primerCupo == null) {
				throw new SiGeVaException(HttpStatus.NOT_FOUND,
						"No se ha podido encontrar ninguna cita libre para su primera dosis. Contacte con el administrador.");
			}

			Cupo segundoCupo = buscarSegundoCupo(primerCupo, listaCupos);

			// Disminuyendo el numero de personas
			primerCupo.restarPersona(1);
			segundoCupo.restarPersona(1);

			Cita primeraCita = new Cita(primerCupo.getFecha(), primerCupo.getHora(), primerCupo.getCentroVacunacion(),
					usuario.getDni());
			Cita segundaCita = new Cita(segundoCupo.getFecha(), segundoCupo.getHora(),
					segundoCupo.getCentroVacunacion(), usuario.getDni());

			citaDao.save(primeraCita);
			citaDao.save(segundaCita);

			// cupor.daoMeter los cupos actualizados

		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private Cupo buscarSegundoCupo(Cupo cupoLibre, List<Cupo> listaCupos) {
		LocalDate fechaPrimera = LocalDate.parse(cupoLibre.getFecha());
		LocalDate fechaSegunda = fechaPrimera.plusDays(21);

		Cupo segundoCupo = buscarCupoLibre(fechaSegunda, listaCupos);

		try {
			if (segundoCupo == null) {
				throw new SiGeVaException(HttpStatus.NOT_FOUND,
						"No se ha podido encontrar ninguna cita libre para la segunda dosis. Contacte con el administrador.");
			}
		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return segundoCupo;
	}

	private Cupo buscarCupoLibre(LocalDate fechaActualDate, List<Cupo> listaCupos) {
		Cupo cupo = null;

		// Para poder coger siempre la primera con un hueco libre por fecha
		listaCupos.sort(Comparator.comparing(Cupo::getFecha));

		for (int i = 0; i < listaCupos.size(); i++) {
			cupo = listaCupos.get(i);
			if (LocalDate.parse(cupo.getFecha()).isAfter(fechaActualDate) && cupo.getPersonasRestantes() > 0) {
				break;
			}
		}
		return cupo;
	}

	@PostMapping("/modificarCita")
	public void modificarCita(HttpSession session, @RequestBody Map<String, Object> datosCita) {

	}

	@DeleteMapping("/anularCita")
	public void anularCita(HttpSession session, @RequestBody String idCita) {
		citaDao.deleteById(idCita);
	}

	@GetMapping("/consultarCita")
	public void consultar(HttpSession session, @RequestBody String idCita) {
		citaDao.findById(idCita);
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

			while (fechaCita.isBefore(LocalDate.parse(LocalDate.now().plusYears(1).getYear() + "-01-01"))) {

				crearCupo(numFranjas, fechaCita, LocalTime.parse(formato.getHoraInicioVacunacion()),
						centrosVacunacion.get(i), formato.getDuracionFranjaVacunacion(),
						formato.getPersonasPorFranja());

				fechaCita = fechaCita.plusDays(1);
			}
		}
	}

	private FormatoVacunacion getFormatoVacunacion() {
		Optional<FormatoVacunacion> optFormato = formatoVacunacionDao.findById("Formato_Unico");
		FormatoVacunacion formatoVacunacion = optFormato.get();
		return formatoVacunacion;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getCitaByDni")
	public Cita getCitaByDni(HttpSession session, @RequestBody Map<String, Object> jsonDni) {
		JSONObject json = new JSONObject(jsonDni);
		return citaDao.findByUsuarioDni(json.getString("dni"));
	}
}

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

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
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

			String fechaActual = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-0"
					+ LocalDate.now().getDayOfMonth();

			LocalDate fechaActualDate = LocalDate.parse(fechaActual);

			List<Cita> listaCitas;
			listaCitas = citaDao.findAllByCentroVacunacion(centroVacunacion);
			
			Cita primeraCita = buscarCitaLibre(fechaActualDate, listaCitas);
				if (primeraCita == null) {
					throw new SiGeVaException(HttpStatus.NOT_FOUND,
							"No se ha podido encontrar ninguna cita libre. Contacte con el administrador.");
				}

				Cita segundaCita = buscarSegundaCita(usuario, primeraCita, listaCitas);

				primeraCita.getListaUsuario().add(usuario);
				segundaCita.getListaUsuario().add(usuario);
				
				citaDao.deleteByFechaAndHora(primeraCita.getFecha(), primeraCita.getHora());
				citaDao.deleteByFechaAndHora(segundaCita.getFecha(), segundaCita.getHora());
				
				citaDao.save(primeraCita);
				citaDao.save(segundaCita);
			}catch(SiGeVaException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

			}
	}

	private Cita buscarSegundaCita(Usuario usuario, Cita citaLibre, List<Cita> listaCitas) {
		LocalDate fechaPrimera = LocalDate.parse(citaLibre.getFecha());
		LocalDate fechaSegunda = fechaPrimera.plusDays(21);

		Cita segundaCita = buscarCitaLibre(fechaSegunda, listaCitas);

		try {
			if (segundaCita == null) {
				throw new SiGeVaException(HttpStatus.NOT_FOUND,
						"No se ha podido encontrar ninguna cita libre. Contacte con el administrador.");
			}
		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return segundaCita;
	}

	private Cita buscarCitaLibre(LocalDate fechaActualDate, List<Cita> listaCitas) {
		Cita cita = null;
		FormatoVacunacion formato = getFormatoVacunacion();

		// Para poder coger siempre la primera con un hueco libre por fecha
		listaCitas.sort(Comparator.comparing(Cita::getFecha));

		for (int i = 0; i < listaCitas.size(); i++) {
			cita = listaCitas.get(i);
			if (LocalDate.parse(cita.getFecha()).isAfter(fechaActualDate)
					&& cita.getListaUsuario().size() < formato.getPersonasPorFranja()) {
				break;
			}
		}
		return cita;
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

	@PostMapping("/crearCita")
	public void crearCita(int numFranjas, LocalDate fecha, LocalTime horaInicio, CentroVacunacion centroVacunacion,
			int duracion) {
		Cita cita;

		for (int i = 0; i < numFranjas; i++) {

			cita = new Cita(fecha.toString(), horaInicio.toString(), centroVacunacion);
			citaDao.save(cita);

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

				crearCita(numFranjas, fechaCita, LocalTime.parse(formato.getHoraInicioVacunacion()),
						centrosVacunacion.get(i), formato.getDuracionFranjaVacunacion());

				fechaCita = fechaCita.plusDays(1);
			}
		}
	}

	private FormatoVacunacion getFormatoVacunacion() {
		Optional<FormatoVacunacion> optFormato = formatoVacunacionDao.findById("Formato_Unico");
		FormatoVacunacion formatoVacunacion = optFormato.get();
		return formatoVacunacion;
	}
}

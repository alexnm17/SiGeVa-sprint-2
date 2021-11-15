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
import edu.esi.uclm.model.EstadoVacunacion;
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
			
			String email = (String) session.getAttribute("email");
			Usuario usuario = usuarioDao.findByEmail(email);

			if (usuario == null)
				throw new SiGeVaException(HttpStatus.NOT_FOUND, "No se ha encontrado ningun usuario con este dni");
	
			if(usuario.getEstadoVacunacion().equals(EstadoVacunacion.VACUNADO_SEGUNDA.name()))
				throw new SiGeVaException(HttpStatus.NOT_FOUND, "El usuario con DNI: "+usuario.getDni()+" ya ha sido vacunado de las dos dosis."
						+ " No puede volver a solicitar cita");
			

			int citasAsignadas = citaDao.findAllByUsuario(usuario).size();

			switch(citasAsignadas) {
			case 0:
				//Primera
				asignarDosis(usuario,LocalDate.now());
				//Segunda
				asignarDosis(usuario,LocalDate.now().plusDays(21));
				break;
			
			case 2:
				throw new SiGeVaException(HttpStatus.FORBIDDEN,
						"El usuario: "+usuario.getDni()+" ya dispone de dos citas asignadas. Si desea modificar su cita, utilice Modificar Cita");

				
			case 1:
				Cita primeraDosis = citaDao.findByUsuario(usuario);
				LocalDate fechaPrimeraCita = LocalDate.parse(primeraDosis.getFecha());

				//Asignar SegundaDosis
				asignarDosis(usuario,fechaPrimeraCita.plusDays(21));
				
				break;
				
			default:
				break;

			}
			
		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}


	private Cupo buscarCupoLibre(LocalDate fechaActualDate,CentroVacunacion centroVacunacion) {
		Cupo cupo = null;
		
		//Para poder coger siempre la primera con un hueco libre por fecha
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

	@PostMapping("/modificarCita")
	public void modificarCita(HttpSession session, @RequestBody Map<String, Object> datosCita) {

	}

	@DeleteMapping("/anularCita")
	public void anularCita(HttpSession session, @RequestBody String idCita) {
		citaDao.deleteById(idCita);
	}

	@GetMapping("/consultarCita")
	public List<Cita> consultar(HttpSession session) {
		try {
			Usuario usuario = usuarioDao.findByEmail((String) session.getAttribute("emailUsuario"));
			List<Cita> citas = citaDao.findAllByUsuario(usuario);
			if (citas.isEmpty())
				throw new SiGeVaException(HttpStatus.NOT_FOUND,
						"No se ha podido encontrar ninguna cita para el usuario. Contacte con el administrador.");

			return citas;

		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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

			while (fechaCita.isBefore(LocalDate.parse(LocalDate.now().plusYears(1).getYear() + "-01-01"))) {

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
		if(optFormato.isPresent())
			formatoVacunacion = optFormato.get();
		else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT,"No existe un formato de Vacunacion definido");
		}
		return formatoVacunacion;
	}
	
	
	public void asignarDosis(Usuario usuario,LocalDate fechaActual) throws SiGeVaException {
		Cupo cupoAsignado = buscarCupoLibre(fechaActual,usuario.getCentroVacunacion());
		if(cupoAsignado == null)
			throw new SiGeVaException(HttpStatus.NOT_FOUND,"No se ha podido encontrar un cupo disponible en estos momentos");
		cupoDao.deleteByFechaAndHoraAndCentroVacunacion(cupoAsignado.getFecha(),cupoAsignado.getHora(),cupoAsignado.getCentroVacunacion());
		cupoAsignado.setPersonasRestantes(cupoAsignado.getPersonasRestantes()-1);
		cupoDao.save(cupoAsignado);
		

		Cita cita= new Cita(cupoAsignado.getFecha(),cupoAsignado.getHora(),cupoAsignado.getCentroVacunacion(), usuario);
		citaDao.save(cita);
		
	}	
	
	@GetMapping("/getCitasPorDia")
	public List<Cita> getCitasPorDia(HttpSession session, @RequestBody Map<String, Object> info) {

		JSONObject json = new JSONObject(info);
		String fecha = json.getString("fecha");
		String email = (String) session.getAttribute("emailUsuario");
		CentroVacunacion centroVacunacion = usuarioDao.findByEmail(email).getCentroVacunacion(); 
		
		return citaDao.findAllByFechaAndCentroVacunacion(fecha, centroVacunacion);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/getCitaByDni")
	public List<Cita> getCitaByDni(HttpServletRequest request, @RequestBody Map<String, Object> info) {
		JSONObject json = new JSONObject(info);
		String dni = json.getString("dni");
		return citaDao.findAllByUsuarioDni(dni);
	}
}

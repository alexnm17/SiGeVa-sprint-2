package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.dao.CitaDao;

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.uclm.esi.exceptions.SiGeVaException;


@RestController
public class CitaController {

	@Autowired
	private FormatoVacunacionDao formatoVacunacionDao;
	@Autowired
	private CitaDao citadao;
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;

	@PostMapping("/solicitarCita")
	public void solicitarCita(HttpSession session) {

	}

	@PostMapping("/modificarCita")
	public void modificarCita(HttpSession session, @RequestBody Map<String, Object> datosCita) {

	}

	@DeleteMapping("/anularCita")
	public void anularCita(HttpSession session, @RequestBody String idCita) {
		citadao.deleteById(idCita);
	}

	@GetMapping("/consultarCita")
	public void consultar(HttpSession session, @RequestBody String idCita) {
		citadao.findById(idCita);
	}

	@PostMapping("/crearCita")
	public void crearCita(int numFranjas, LocalDate fecha, LocalTime horaInicio, CentroVacunacion centroVacunacion,
			int duracion) {
		Cita cita;

		for (int i = 0; i < numFranjas; i++) {

			cita = new Cita(fecha.toString(), horaInicio.toString(), centroVacunacion);
			citadao.save(cita);

			horaInicio = horaInicio.plusMinutes(duracion);

		}

	}

	@GetMapping("/crearPlantillasCitaVacunacion")
	public void crearPlantillasCitaVacunacion() throws SiGeVaException {
		Optional<FormatoVacunacion> optformato = formatoVacunacionDao.findById("61786ae2d452371261588e26");
		FormatoVacunacion formato= null;
		if (!optformato.isPresent()) throw new SiGeVaException(HttpStatus.NOT_FOUND,"No se ha encontrado el componente");
			formato = optformato.get();

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
}

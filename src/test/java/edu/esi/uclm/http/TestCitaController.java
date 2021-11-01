package edu.esi.uclm.http;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.uclm.esi.exceptions.SiGeVaException;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
public class TestCitaController extends TestCase {
	private CitaController citacontroller = new CitaController();
	private CentroVacunacionDao dao;

	@Test
	private void testSolicitarCitaCorrecta() {
		Optional<CentroVacunacion> centro = dao.findById("61793aebb7bbfc0c001774c2");
		LocalDate hoy = LocalDate.now();
		int duracion = 10;
		LocalTime horaInicio = LocalTime.parse("12:00");
		citacontroller.crearCita(3, hoy, horaInicio, centro.get(), duracion);
	}

	@Test
	private void testSolicitarCitaFalloNoUsuario() {
		String idfalso = "NoExiste";
		Map<String, Object> objeto = new HashMap<String, Object>();
		objeto.put("dni", idfalso);
		Exception e = assertThrows(SiGeVaException.class, () -> citacontroller.solicitarCita(null, objeto));
		fail("A pesar de que no existe ningun usuario con este dni recoge a uno y continua con el procedimiento normal");
		String mensajeException = e.getMessage();
		String mensaje_esperado = "No se ha encontrado ningun usuario con este dni";
		assertEquals(mensaje_esperado, mensajeException);
	}

	/*
	 * @Test private void testSolicitarCitaFalloNoCita() { String id= "a";
	 * Map<String,Object> objeto = new HashMap<String,Object>(); objeto.put("dni",
	 * id); Exception e = assertThrows(SiGeVaException.class, () ->
	 * citacontroller.solicitarCita(null,objeto));
	 * fail("A pesar de que no existe ningun usuario con este dni recoge a uno y continua con el procedimiento normal"
	 * ); String mensajeException = e.getMessage(); String mensaje_esperado =
	 * "No se ha encontrado ningun usuario con este dni";
	 * assertTrue(mensaje_esperado.equals(mensajeException)); }
	 */

	@Test
	private void testBuscarCitaLibreCorrecto() {
		LocalDate actual = LocalDate.now();
		List<Cita> citasDisponibles = new ArrayList<Cita>();
		CentroVacunacion centroVacunacion;

		centroVacunacion = dao.findByNombre("Tomelloso");

		Cita cita = new Cita(actual.plusDays(1).toString(), "12:30", centroVacunacion);

		//assertNotNull(citacontroller.buscarCitaLibre(actual, citasDisponibles));
		fail("Aunque haya una cita disponible ");
	}

	@Test
	private void testBuscarCitaLibreNoEncuentra() {
		LocalDate actual = LocalDate.now();
		List<Cita> citasDisponibles = new ArrayList<Cita>();

		//assertNull(citacontroller.buscarCitaLibre(actual, citasDisponibles));
		fail("A pesar de que la lista es vacia, ha recogido una cita");
	}
}

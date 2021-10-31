package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.uclm.esi.exceptions.SiGeVaException;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
public class TestCitaController extends TestCase{
	private CitaController citacontroller= new CitaController(); 
	private CentroVacunacionDao dao;
		@Test
		private void testCrearCitaCorrecta() {
			Optional<CentroVacunacion> centro = dao.findById("61793aebb7bbfc0c001774c2");
			LocalDate hoy = LocalDate.now();
			int duracion = 10;
			LocalTime horaInicio = LocalTime.parse("12:00");
			 citacontroller.crearCita(3,hoy,horaInicio,centro.get(), duracion); 
			
		}
		
		private void testCrearCitaFallo() {
			
		}
		
		private void testCrearPlantillasVacunacionCorrecta() {
			 try {
				citacontroller.crearPlantillasCitaVacunacion();
			} catch (SiGeVaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
	
}

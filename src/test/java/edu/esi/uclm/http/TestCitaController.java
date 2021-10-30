package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.exceptions.SiGeVaException;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
public class TestCitaController extends TestCase{
	private CitaController citacontroller= new CitaController(); 
		@Test
		private void testCrearCitaCorrecta() {
			LocalDate hoy = LocalDate.now();
			int horaFin = 10;
			int horaInicio = 22;
			 citacontroller.crearCita(3,hoy,); 
			
		}
		
		private void testCrearCitaFallo() {
			 citacontroller 
			
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

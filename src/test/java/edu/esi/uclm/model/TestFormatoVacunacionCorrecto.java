package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestFormatoVacunacionCorrecto {

	@Test
	public void test() {
		FormatoVacunacion formato = new FormatoVacunacion("12:30", "16:30", 30, 10);
		try {
			boolean resultado = formato.horasCorrectas();
			assertTrue(resultado);
		} catch (SiGeVaException e) {
			
			e.printStackTrace();
		}
	}

}

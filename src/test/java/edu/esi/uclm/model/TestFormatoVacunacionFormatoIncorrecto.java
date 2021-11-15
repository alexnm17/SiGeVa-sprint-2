package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;

class TestFormatoVacunacionFormatoIncorrecto {

	@Test
	public void test() {
		FormatoVacunacion formato = new FormatoVacunacion("12:30", "fallo", 30, 10);

		Exception exception = assertThrows(SigevaException.class, () -> formato.horasCorrectas());

		String expectedMessage = "El formato introducido no es válido ";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}

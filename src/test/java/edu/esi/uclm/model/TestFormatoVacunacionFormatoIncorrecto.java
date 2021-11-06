package edu.esi.uclm.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestFormatoVacunacionFormatoIncorrecto {

	@Test
	public void test() {
		FormatoVacunacion formato = new FormatoVacunacion("12:30", "fallo", 30, 10);

		Exception exception = assertThrows(SiGeVaException.class, () -> formato.horasCorrectas());
		//fail("Acepta el nuevo texto como un formato de horas");

		String expectedMessage = "El formato introducido no es válido ";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}

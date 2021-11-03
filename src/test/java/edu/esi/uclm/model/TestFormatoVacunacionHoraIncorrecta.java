package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestFormatoVacunacionHoraIncorrecta {

	@Test
	public void test() {
		try {
			FormatoVacunacion formato = new FormatoVacunacion("17:00", "16:30", 30, 10);

			assertFalse(formato.horasCorrectas());
		} catch (Exception e) {

		}
	}

}

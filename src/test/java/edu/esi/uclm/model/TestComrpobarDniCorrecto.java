package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestComrpobarDniCorrecto {

	@Test
	void test() {
		Usuario user = new Usuario("12345678B", "", "", null, null, null);
		try {
			user.comprobarDni();
			throw new Exception("Se ha completado este proceso correctamente");
		} catch (Exception e) {
			assertEquals("Se ha completado este proceso correctamente", e.getMessage());
		}
	}

}

package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.utils.AuxiliaryMethods;


class TestComrpobarDniCorrecto {

	@Test
	void test() {
		String dni = "01234987Q";
		try {
			AuxiliaryMethods.comprobarDni(dni);
			throw new Exception("Se ha completado este proceso correctamente");
		} catch (Exception e) {
			assertEquals("Se ha completado este proceso correctamente", e.getMessage());
		}
	}

}

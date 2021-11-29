package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.utils.AuxiliaryMethods;


class TestContrasenaValida {

	@Test
	void test() {
		try {
			AuxiliaryMethods.controlarContrasena("Contraseña_123");
			throw new Exception("Se ha completado este proceso correctamente");
		} catch (Exception e) {
			assertEquals("Se ha completado este proceso correctamente", e.getMessage());
		}
	}

}

package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestDniSinLetra {

	@Test
	void test() {

		Usuario user = new Usuario("987654321", "", "", null, null, null);

		Exception e = assertThrows(SiGeVaException.class, () -> user.comprobarDni());
		assertEquals("No cumple con el formato de un DNI", e.getMessage());

	}

}

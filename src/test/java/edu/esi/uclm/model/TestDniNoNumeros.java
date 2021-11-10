package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestDniNoNumeros {

	@Test
	void test() {
		Usuario user = new Usuario("abf23452B", "", "", null, null, null);

		Exception e=assertThrows(SiGeVaException.class, () -> user.comprobarDni());
		assertEquals("No cumple con el formato de un DNI",e.getMessage());
	}

}

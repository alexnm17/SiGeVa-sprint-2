package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.utils.AuxiliaryMethods;

class TestDniLongitudIncorrecta {

	@Test
	void test() {
		Exception e = assertThrows(SigevaException.class, () -> AuxiliaryMethods.comprobarDni("Corta"));
		assertEquals("No cumple con el formato de un DNI", e.getMessage());

	}

}

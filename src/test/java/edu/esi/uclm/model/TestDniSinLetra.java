package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.utils.AuxiliaryMethods;

class TestDniSinLetra {

	@Test
	void test() {

		Exception e = assertThrows(SigevaException.class, () -> AuxiliaryMethods.comprobarDni("123456789"));
		assertEquals("No cumple con el formato de un DNI", e.getMessage());

	}

}

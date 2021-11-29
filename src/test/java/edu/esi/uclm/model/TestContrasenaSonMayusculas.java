package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.utils.AuxiliaryMethods;

class TestContrasenaSonMayusculas {

	@Test
	void test() {
		try {
			AuxiliaryMethods.controlarContrasena("contraseña");
		} catch (Exception e) {
			assertEquals("La contraseña no contiene una letra mayuscula", e.getMessage());
		}
	}
}

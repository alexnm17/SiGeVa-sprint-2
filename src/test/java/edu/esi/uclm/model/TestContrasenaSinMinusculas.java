package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.utils.AuxiliaryMethods;

class TestContrasenaSinMinusculas {

	@Test
	void test() {
		try {
			AuxiliaryMethods.controlarContrasena("CONTRASEÑA");
		} catch (Exception e) {
			assertEquals("La contraseña no contiene una letra minuscula", e.getMessage());
		}
	}

}

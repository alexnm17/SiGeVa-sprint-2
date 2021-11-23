package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.utils.AuxiliaryMethods;

class TestConstrasenaCorta {

	@Test
	void test() {
		assertThrows(SigevaException.class, () -> AuxiliaryMethods.controlarContrasena("corta"));

	}

}

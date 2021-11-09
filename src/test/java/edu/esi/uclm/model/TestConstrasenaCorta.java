package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestConstrasenaCorta {

	@Test
	void test() {
		Usuario user = new Usuario("87654321R", "probamos4", "testermodif", "corta", "Paciente", "El bombo");

		assertThrows(SiGeVaException.class, () -> user.controlarContrasena());

	}

}

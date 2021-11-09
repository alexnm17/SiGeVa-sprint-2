package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestContrasenaValida {

	@Test
	void test() {
		Usuario user = new Usuario("87654321R", "probamos4", "testermodif", "bbbbBbbbbbb", "Paciente", "El bombo");
		try {
			user.controlarContrasena();
			throw new Exception("Se ha completado este proceso correctamente");
		} catch (Exception e) {
			assertEquals("Se ha completado este proceso correctamente", e.getMessage());
		}
	}

}

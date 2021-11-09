package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestContrasenaSinMinusculas {

	@Test
	void test() {
		Usuario user = new Usuario("87654321R", "probamos4", "testermodif", "BBBBBBBBBBBB", "Paciente", "El bombo");
		try {
			user.controlarContrasena();
		} catch (Exception e) {
			assertEquals("La contraseña no contiene una letra minuscula", e.getMessage());
		}
	}

}

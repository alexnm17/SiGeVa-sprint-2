package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestContrasenaSinMinusculas {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","123456789", "Pepe", "Garcia", "CONTRASEÑA", "Paciente",centro);
		try {
			user.controlarContrasena();
		} catch (Exception e) {
			assertEquals("La contraseña no contiene una letra minuscula", e.getMessage());
		}
	}

}

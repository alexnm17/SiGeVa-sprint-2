package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestContrasenaSonMayusculas {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","123456789", "Pepe", "Garcia", "contraseña", "Paciente",centro);
		try {
			user.controlarContrasena();
		} catch (Exception e) {
			assertEquals("La contraseña no contiene una letra mayuscula", e.getMessage());
		}
	}
}

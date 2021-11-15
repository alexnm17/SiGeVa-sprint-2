package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;

class TestDniLongitudIncorrecta {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","Corta", "Pepe", "Garcia", "Contraseña", "Paciente",centro);

		Exception e = assertThrows(SigevaException.class, () -> user.comprobarDni());
		assertEquals("No cumple con el formato de un DNI", e.getMessage());

	}

}

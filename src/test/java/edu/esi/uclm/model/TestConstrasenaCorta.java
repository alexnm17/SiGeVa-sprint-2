package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.exceptions.SigevaException;

class TestConstrasenaCorta {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","123456789", "Pepe", "Garcia", "corta", "Paciente",centro);

		assertThrows(SigevaException.class, () -> user.controlarContrasena());

	}

}

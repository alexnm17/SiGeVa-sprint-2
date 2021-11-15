package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestConstrasenaCorta {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","123456789", "Pepe", "Garcia", "corta", "Paciente",centro);

		assertThrows(SiGeVaException.class, () -> user.controlarContrasena());

	}

}

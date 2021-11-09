package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestEstadoNoAceptado {

	@Test
	void test() {
		Usuario user = new Usuario("12345678A", "Pepe", "Garcia", "Contraseña", "Administrador", "Tomelloso");
		user.setEstadoVacunacion("Primera Vacuna");
		assertThrows(SiGeVaException.class, () -> user.comprobarEstado());
	}
}

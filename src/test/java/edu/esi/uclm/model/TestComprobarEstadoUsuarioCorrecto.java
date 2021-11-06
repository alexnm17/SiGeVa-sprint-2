package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestComprobarEstadoUsuarioCorrecto {

	@Test
	void test() {
		try {
		Usuario user = new Usuario("1234567A", "Pepe", "Garcia", "Contraseña", "Paciente","Tomelloso", "No Vacunado");
		user.comprobarEstado();
		throw new Exception("Se ha completado este proceso correctamente");
		}catch(Exception e) {
			assertEquals("Se ha completado este proceso correctamente",e.getMessage());
		}
	}

}

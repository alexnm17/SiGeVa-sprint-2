package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestComrpobarDniCorrecto {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","12345678A", "Pepe", "Garcia", "Contraseña", "Paciente",centro);
		try {
			user.comprobarDni();
			throw new Exception("Se ha completado este proceso correctamente");
		} catch (Exception e) {
			assertEquals("Se ha completado este proceso correctamente", e.getMessage());
		}
	}

}

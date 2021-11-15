package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestContrasenaValida {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","123456789", "Pepe", "Garcia", "Contraseña", "Paciente",centro);
		try {
			user.controlarContrasena();
			throw new Exception("Se ha completado este proceso correctamente");
		} catch (Exception e) {
			assertEquals("Se ha completado este proceso correctamente", e.getMessage());
		}
	}

}

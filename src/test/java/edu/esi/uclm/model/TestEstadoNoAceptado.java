package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestEstadoNoAceptado {

	@Test
	void test() {
		CentroVacunacion centro= new CentroVacunacion();
		Usuario user = new Usuario("prueba@email.com","12345678A", "Pepe", "Garcia", "Contraseña", "Administrador",centro);
		user.setEstadoVacunacion(EstadoVacunacion.VACUNADO_PRIMERA.name());
		assertThrows(SiGeVaException.class, () -> user.comprobarEstado());
	}
}

package edu.esi.uclm.http;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.http.UsuarioController;
import edu.uclm.esi.exceptions.SiGeVaException;

class TestEliminarUsuarioAdmin {
	private UsuarioController usuarioController = new UsuarioController();

	@Test
	void test() {
		Exception e = assertThrows(ResponseStatusException.class, () -> usuarioController.eliminarUsuario("PruebaAdmin"));
		assertEquals("No puede eliminar a otro administrador del sistema",e.getMessage());
	}

}

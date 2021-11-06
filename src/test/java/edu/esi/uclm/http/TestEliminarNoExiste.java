package edu.esi.uclm.http;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.http.UsuarioController;
import edu.uclm.esi.exceptions.SiGeVaException;

class TestEliminarNoExiste {
	
	private UsuarioController usuarioController= new UsuarioController();
	@Test
	void test() {
		Exception e = assertThrows(SiGeVaException.class, () -> usuarioController.eliminarUsuario("87654321"));
		Assert.assertEquals(e.getMessage(), "No existe un usuario con este identificador");
	}

}

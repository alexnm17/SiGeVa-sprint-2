package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.web.server.ResponseStatusException;

@DataMongoTest
class TestEliminarUsuarioAdmin {
	private UsuarioController usuarioController = new UsuarioController();

	@Test
	void test() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni", "PruebaAdmin");
		Exception e = assertThrows(ResponseStatusException.class, () -> usuarioController.eliminarUsuario(datos));
		assertEquals("No puede eliminar a otro administrador del sistema", e.getMessage());
	}

}

package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.web.server.ResponseStatusException;
import edu.esi.uclm.model.Usuario;

@DataMongoTest
class TestModificarUsuarioNoExiste {

	private UsuarioController usuarioController = new UsuarioController();

	@Test
	void test() {
		Usuario user = new Usuario("87654321R", "testmod", "testermodif", "bbbbbbbbbbb", "Paciente", "El bombo");
		Exception e = assertThrows(ResponseStatusException.class, () -> usuarioController.modificarUsuario(user));

		Assert.assertEquals("409 CONFLICT No existe un usuario con este identificador", e.getMessage());

	}

}

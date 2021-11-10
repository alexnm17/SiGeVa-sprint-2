package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.http.UsuarioController;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

@DataMongoTest
class TestModificarUsuarioAdmin {
	
	private UsuarioController usuarioController= new UsuarioController();

	@Test
	void test() {
		Usuario user = new Usuario("PruebaAdmin", "testadm", "testeradmin", "bbbbbbbbbbb", "Tomelloso",
				RolUsuario.ADMINISTRADOR.name());
		Exception e = assertThrows(ResponseStatusException.class, () -> usuarioController.modificarUsuario(user));
		assertEquals( "409 CONFLICT No puede modificar a otro administrador del sistema",e.getMessage());
	}
	
	
}

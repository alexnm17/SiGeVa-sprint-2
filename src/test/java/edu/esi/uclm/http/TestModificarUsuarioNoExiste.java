package edu.esi.uclm.http;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.http.UsuarioController;
import edu.esi.uclm.model.Usuario;
import edu.uclm.esi.exceptions.SiGeVaException;

class TestModificarUsuarioNoExiste {

	private UsuarioController usuarioController= new UsuarioController();
	
	@Test
	void test() {
		Usuario user= new Usuario("87654321","testmod","testermodif","bbbbbbbbbbb","Tomelloso","Paciente");
		Exception e = assertThrows(ResponseStatusException.class,()->usuarioController.modificarUsuario(user));
		
		Assert.assertEquals("409 CONFLICT No existe un usuario con este identificador",e.getMessage());
		
	}

}

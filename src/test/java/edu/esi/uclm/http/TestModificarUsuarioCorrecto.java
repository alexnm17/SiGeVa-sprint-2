package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.http.UsuarioController;
import edu.esi.uclm.model.Usuario;

class TestModificarUsuarioCorrecto {
	private UsuarioController usuarioController= new UsuarioController();
	private UsuarioDao dao;
	@Test
	void test() {
		
		Usuario user= new Usuario("Prueba","Sujetomod","Pruebamodif","maniqui","Tomelloso","Paciente");
		usuarioController.modificarUsuario(user);
		Usuario resultado = dao.findByDni("Prueba");
		Assert.assertEquals(user, resultado);
	}

}

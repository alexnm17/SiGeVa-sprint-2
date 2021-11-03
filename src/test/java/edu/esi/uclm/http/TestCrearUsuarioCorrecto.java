package edu.esi.uclm.http;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.Usuario;

class TestCrearUsuarioCorrecto {

	UsuarioController usuarioController= new UsuarioController();
	@Autowired
	private UsuarioDao dao;
	
	@Test
	void test() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni","12345678");
		datos.put("nombre","test");
		datos.put("apellido","tester");
		datos.put("password","aaaaaaaaaaaa");
		datos.put("centroSalud","Tomelloso");
		datos.put("rol","Paciente");
		
		Usuario user= new Usuario("12345678","test","tester","aaaaaaaaaaaa","Tomelloso","Paciente");
		
		usuarioController.crearUsuario(datos);
		Usuario resultado = dao.findByDni("12345678");
		Assert.assertEquals(user, resultado);
		usuarioController.eliminarUsuario("12345678");
	}

}

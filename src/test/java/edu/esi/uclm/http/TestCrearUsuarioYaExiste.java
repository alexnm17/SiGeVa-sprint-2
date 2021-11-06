package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.UsuarioDao;

class TestCrearUsuarioYaExiste {

	UsuarioController usuarioController= new UsuarioController();
	
	@Autowired
	UsuarioDao dao;
	
	@Test
	void test() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni", "Prueba");
		datos.put("nombre", "Sujeto");
		datos.put("apellido", "Prueba");
		datos.put("password", "maniqui");
		datos.put("centroSalud", "Tomelloso");
		datos.put("rol", "Paciente");

		usuarioController.crearUsuario(datos);
		
		
		assertThrows(ResponseStatusException.class, ()-> usuarioController.crearUsuario(datos));
		
	
	}

}

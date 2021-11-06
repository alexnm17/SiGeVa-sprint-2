package edu.esi.uclm.http;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

class TestCrearUsuarioCorrecto {

	UsuarioController usuarioController= new UsuarioController();
	@Autowired
	private UsuarioDao dao;
	
	@Test
	void test() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni","02678961Y");
		datos.put("nombre","test");
		datos.put("apellido","tester");
		datos.put("password","HolaHola1");
		datos.put("centroSalud","Tomelloso");
		datos.put("rol",RolUsuario.PACIENTE.name());
		
		Usuario user= new Usuario("02678961Y","test","tester","HolaHola1","Tomelloso",RolUsuario.PACIENTE.name(),EstadoVacunacion.NO_VACUNADO.name());
		
		usuarioController.crearUsuario(datos);
		Usuario resultado = dao.findByDni(user.getDni());
		Assert.assertEquals(user, resultado);
	}

}

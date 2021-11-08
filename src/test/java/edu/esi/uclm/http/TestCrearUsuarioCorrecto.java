package edu.esi.uclm.http;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;
import edu.uclm.esi.exceptions.SiGeVaException;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TestCrearUsuarioCorrecto {


	@Autowired
	private UsuarioDao dao;

	
	@Test 
	public void test() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni","02678961Y");
		datos.put("nombre","test");
		datos.put("apellido","tester");
		datos.put("password","HolaHola1");
		datos.put("centroSalud","Tomelloso");
		datos.put("rol",RolUsuario.PACIENTE.name());
		
		Usuario user= new Usuario("02678961Y","test","tester","HolaHola1","Tomelloso",RolUsuario.PACIENTE.name(),EstadoVacunacion.NO_VACUNADO.name());
		try {
			user.controlarContraseña();
			user.comprobarDni();
		} catch (SiGeVaException e) {
			e.printStackTrace();
		}
	

		Usuario resultado = dao.findByDni(user.getDni());
		Assert.assertEquals(user, resultado);
	}

}

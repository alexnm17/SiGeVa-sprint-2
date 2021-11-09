package edu.esi.uclm.http;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.http.UsuarioController;
import edu.esi.uclm.model.Usuario;

class TestEliminarUsuarioCorrecto {
	
	private UsuarioController usuarioController= new UsuarioController();
	private UsuarioDao dao;
	
	@Test
	void test() {

		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni","Eliminar");
		datos.put("nombre","Para");
		datos.put("apellido","El");
		datos.put("password","Test");
		datos.put("centroSalud","Tomelloso");
		datos.put("rol","Paciente");
		
		
		usuarioController.crearUsuario(datos);
		Usuario resultado = dao.findByDni("Eliminar");
		assertNotNull(resultado);
		
		usuarioController.eliminarUsuario("Eliminar");
		
		assertThrows(Exception.class,()->dao.findByDni("Eliminar"));

	}

}

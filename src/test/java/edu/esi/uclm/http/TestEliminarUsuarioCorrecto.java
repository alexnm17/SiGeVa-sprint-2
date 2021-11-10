package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;
@DataMongoTest
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
		datos.put("rol",RolUsuario.PACIENTE.name());


		usuarioController.crearUsuario(datos);
		Usuario resultado = dao.findByDni("Eliminar");
		assertNotNull(resultado);

		usuarioController.eliminarUsuario(datos);

		assertThrows(Exception.class,()->dao.findByDni("Eliminar"));

	}

}

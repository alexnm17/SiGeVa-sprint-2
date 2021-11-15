package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.http.UsuarioController;
@DataMongoTest
class TestEliminarNoExiste {
	
	private UsuarioController usuarioController= new UsuarioController();
	@Test
	void test() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni","Eliminar");
		Exception e = assertThrows(SigevaException.class, () -> usuarioController.eliminarUsuario(datos));
		Assert.assertEquals( "No existe un usuario con este identificador",e.getMessage());
	}

}

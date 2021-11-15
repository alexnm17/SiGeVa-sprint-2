package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.http.UsuarioController;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

@DataMongoTest
class TestModificarUsuarioAdmin {
	
	private UsuarioController usuarioController= new UsuarioController();

	@Test
	void test() {
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.ADMINISTRADOR.name(), centro);
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email","");
		mapa.put("dni","");
		mapa.put("nombre","");
		mapa.put("apellido","");
		mapa.put("contrasena","");
		mapa.put("contrasena","");
		mapa.put("centro",centro);
		Exception e = assertThrows(ResponseStatusException.class, () -> usuarioController.modificarUsuario(mapa));
		assertEquals( "409 CONFLICT No puede modificar a otro administrador del sistema",e.getMessage());
	}
	
	
}

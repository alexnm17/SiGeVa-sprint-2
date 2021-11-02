package edu.esi.uclm.http;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Usuario;
import edu.uclm.esi.exceptions.SiGeVaException;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
class TestUsuarioController {
	private UsuarioController usuarioController= new UsuarioController();
	private UsuarioDao dao;
	
	@Test
	private void TestCrearUsuarioCorrectoYaexisteUsuario() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("dni",12345678);
		datos.put("nombre","test");
		datos.put("apellido","tester");
		datos.put("password","aaaaaaaaaaaa");
		datos.put("centroSalud","Tomelloso");
		datos.put("rol","Paciente");
		
		Usuario user= new Usuario("12345678","test","tester","aaaaaaaaaaaa","Tomelloso","Paciente");
		
		usuarioController.crearUsuario(datos);
		Usuario resultado = dao.findByDni("12345678");
		Assert.assertEquals(user, resultado);
		
		Exception exception = assertThrows(SiGeVaException.class, ()->usuarioController.crearUsuario(datos));
		Assert.fail("Acepta introducir otro usuario con el mismo dni, lo que no deberia ser posible");
	}
	
	@Test
	private void TestModificarUsuarioCorrecto() {
		Usuario user= new Usuario("12345678","testmod","testermodif","bbbbbbbbbbb","Tomelloso","Paciente");
		usuarioController.modificarUsuario(user);
		Usuario resultado = dao.findByDni("12345678");
		Assert.assertEquals(user, resultado);
	}
	
	@Test
	private void TestModificarUsuarioNoExisteUsuario() {
		Usuario user= new Usuario("87654321","testmod","testermodif","bbbbbbbbbbb","Tomelloso","Paciente");
		Exception e = assertThrows(SiGeVaException.class,()->usuarioController.modificarUsuario(user));
		Assert.fail("Aunque no haya ningun usuario con este id, sigue funcionando el programa");
		Assert.assertEquals(e.getMessage(), "No existe un usuario con este identificador");
		
	}
	
	@Test
	private void TestModificarUsuarioIntentaModificarAdmin() {
		Usuario user= new Usuario("6180331bbdfa2a3a672492b6","testadm","testeradmin","bbbbbbbbbbb","Tomelloso","Administrador");
		Exception e = assertThrows(SiGeVaException.class,()->usuarioController.modificarUsuario(user));
		Assert.fail("Aunque el usuario sea un administrador, nos permite modificarlo");
		Assert.assertEquals(e.getMessage(), "No puede modificar a otro administrador del sistema");
		
	}
	
	@Test
	private void TestEliminarUsuarioCorrecto() {
		usuarioController.eliminarUsuario("Prueba");
		Usuario resultado = dao.findByDni("12345678");
		Assert.assertEquals(null, resultado);
	}
	
	@Test
	private void TestElimnarUsuarioNoExisteUsuario() {
		Exception e = assertThrows(SiGeVaException.class,()->usuarioController.eliminarUsuario("87654321"));
		Assert.fail("Aunque no haya ningun usuario con este id, sigue funcionando el programa");
		Assert.assertEquals(e.getMessage(), "No existe un usuario con este identificador");
		
	}
	
	@Test
	private void TestEliminarUsuarioIntentaEliminarAdmin() {
		Exception e = assertThrows(SiGeVaException.class,()->usuarioController.eliminarUsuario("PruebaAdmin"));
		Assert.fail("Aunque el usuario sea un administrador, nos permite eliminarlo");
		Assert.assertEquals(e.getMessage(), "No puede eliminar a otro administrador del sistema");
		
	}
	
}

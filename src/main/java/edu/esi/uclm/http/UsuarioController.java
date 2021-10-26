package edu.esi.uclm.http;

import edu.esi.uclm.model.Usuario;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.esi.uclm.dao.UsuarioDao;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
	private UsuarioDao usuariodao;

	@PostMapping("/crearUsuario")
	public void addUsuario(String dni, String nombre, String apellido, String password) {
		Usuario user = new Usuario(dni, nombre, apellido, password);
		usuariodao.save(user);

	}

	@PostMapping("/modificarUsuario")
	public void modificarUsuario(String dni, String nombre, String apellido, String password) {
		try {
			
		
		Optional<Usuario> antiguo= usuariodao.findById(dni);
		antiguo.get().setPassword(password);
		usuariodao.deleteById(dni);
		usuariodao.save(antiguo.get());
		}catch(Exception e) {
			
		}
	}

	@DeleteMapping("/eliminarUsuario")
	public void eliminarUsuario(String dni) {
		usuariodao.deleteById(dni);

	}
}

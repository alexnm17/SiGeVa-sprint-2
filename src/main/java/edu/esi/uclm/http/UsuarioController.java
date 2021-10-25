package edu.esi.uclm.http;

import edu.esi.uclm.model.Usuario;

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
		usuariodao.insert(user);

	}

	@PostMapping("/modificarUsuario")
	public void modificarUsuario(String dni, String nombre, String apellido, String password) {
		Usuario user = new Usuario(dni, nombre, apellido, password);

	}

	@DeleteMapping("/eliminarUsuario")
	public void eliminarUsuario(String dni) {
		usuariodao.deleteById(dni);

	}
}

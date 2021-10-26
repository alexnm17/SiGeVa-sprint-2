package edu.esi.uclm.http;

import edu.esi.uclm.model.Usuario;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.Usuario;

@RestController
public class UsuarioController {
	private UsuarioDao usuariodao;

	@PostMapping("/crearUsuario")
	public void addUsuario(String dni, String nombre, String apellido, String password, String centroSalud) {
		Usuario user = new Usuario(dni, nombre, apellido, password, centroSalud);
		usuariodao.insert(user);

	}

	@PostMapping("/modificarUsuario")
	public void modificarUsuario(String dni, String nombre, String apellido, String password, String centroSalud) {
		Usuario user = new Usuario(dni, nombre, apellido, password, centroSalud);

	}

	@DeleteMapping("/eliminarUsuario")
	public void eliminarUsuario(String dni) {
		usuariodao.deleteById(dni);

	}
}

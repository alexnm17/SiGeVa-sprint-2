package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.esi.uclm.model.Usuario;
import edu.uclm.esi.exceptions.SiGeVaException;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.RolUsuario;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioDao userDao;
	private CitaDao citaDao;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/crearUsuario")
	public void crearUsuario(@RequestBody Map<String, Object> datosUsuario) {
		try {

			JSONObject json = new JSONObject(datosUsuario);
			String dni = json.getString("dni");
			String nombre = json.getString("nombre");
			String apellido = json.getString("apellido");
			String password = json.getString("password");
			String centroSalud = json.getString("centroSalud");
			String estadoPaciente = "No Vacunado";
			String rol = json.getString("rol");
			Usuario nuevoUsuario = new Usuario(dni, nombre, apellido, password, rol, centroSalud, estadoPaciente);
			nuevoUsuario.controlarContraseña();
			nuevoUsuario.comprobarDni();
			userDao.save(nuevoUsuario);
		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/modificarUsuario")
	public void modificarUsuario(@RequestBody Usuario user) {
		try {
			if (user.getRol().equals(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede modificar a otro administrador del sistema");
			else {
				Usuario antiguoUsuario = userDao.findByDni(user.getDni());
				if (antiguoUsuario == null)
					throw new SiGeVaException(HttpStatus.NOT_FOUND, "No existe un usuario con este identificador");
				antiguoUsuario.setNombre(user.getNombre());
				antiguoUsuario.setApellido(user.getApellido());
				if (!antiguoUsuario.getCentroSalud().equals(user.getCentroSalud()))
					antiguoUsuario.comprobarEstado();
				antiguoUsuario.setCentroSalud(user.getCentroSalud());
				antiguoUsuario.setPassword(user.getPassword());

				antiguoUsuario.controlarContraseña();
				userDao.save(antiguoUsuario);
			}

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@DeleteMapping("/eliminarUsuario/{dni}")
	public void eliminarUsuario(@PathVariable String dni) {
		try {
			Usuario user = userDao.findByDni(dni);
			if (user == null)
				throw new SigevaException(HttpStatus.FORBIDDEN, "No existe este usuario");
			if (user.getRol().equals(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a otro administrador del sistema");
			if (user.getRol().equals("Paciente")) {
				user.comprobarEstado();
			}
			BorrarCitas(dni);
			userDao.delete(user);

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	private void BorrarCitas(String dni) {
		citaDao.deleteAllByUsuarioDni(dni);
	}

}

package edu.esi.uclm.http;

<<<<<<< HEAD
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
=======
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.esi.uclm.model.Usuario;
import edu.uclm.esi.exceptions.SiGeVaException;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.RolUsuario;

@RestController
public class UsuarioController {

	@Autowired
<<<<<<< HEAD
	private UsuarioDao userDao;
	private CitaDao citaDao;
=======
	private UsuarioDao usuarioDao;
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/crearUsuario")
	public void crearUsuario(@RequestBody Map<String, Object> datosUsuario) {
<<<<<<< HEAD
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
=======
		JSONObject json = new JSONObject(datosUsuario);
		String dni = json.getString("dni");
		String nombre = json.getString("nombre");
		String apellido = json.getString("apellido");
		String password = json.getString("password");
		String centroSalud = json.getString("centroSalud");
		String rol = json.getString("rol");
		Usuario nuevoUsuario = new Usuario(dni, nombre, apellido, password, rol, centroSalud);
		usuarioDao.save(nuevoUsuario);
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
	}

	@PostMapping("/modificarUsuario")
	public void modificarUsuario(@RequestBody Usuario user) {
		try {
			if (user.getRol().equals(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede modificar a otro administrador del sistema");
			else {
<<<<<<< HEAD
				Usuario antiguoUsuario = userDao.findByDni(user.getDni());
=======
				System.out.print("Hola, estoy modificando al usuario:\t DNI: " + user.getDni() + " || Nombre: "
						+ user.getNombre());

				Usuario antiguoUsuario = usuarioDao.findByDni(user.getDni());
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
				if (antiguoUsuario == null)
					throw new SiGeVaException(HttpStatus.NOT_FOUND, "No existe un usuario con este identificador");
				antiguoUsuario.setNombre(user.getNombre());
				antiguoUsuario.setApellido(user.getApellido());
				if (!antiguoUsuario.getCentroSalud().equals(user.getCentroSalud()))
					antiguoUsuario.comprobarEstado();
				antiguoUsuario.setCentroSalud(user.getCentroSalud());
				antiguoUsuario.setPassword(user.getPassword());

<<<<<<< HEAD
				antiguoUsuario.controlarContraseña();
				userDao.save(antiguoUsuario);
=======
				usuarioDao.save(antiguoUsuario);
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
			}

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/eliminarUsuario")
	public void eliminarUsuario(@RequestBody Map<String, Object> datosUsuario) {
		try {
<<<<<<< HEAD
			Usuario user = userDao.findByDni(dni);
			if (user == null)
				throw new SigevaException(HttpStatus.FORBIDDEN, "No existe este usuario");
=======
			JSONObject json = new JSONObject(datosUsuario);
			String dni = json.getString("dni");

			Usuario user = usuarioDao.findByDni(dni);

>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
			if (user.getRol().equals(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a otro administrador del sistema");
<<<<<<< HEAD
			if (user.getRol().equals("Paciente")) {
				user.comprobarEstado();
			}
			BorrarCitas(dni);
			userDao.delete(user);
=======
			else
				usuarioDao.delete(user);
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

<<<<<<< HEAD
	private void BorrarCitas(String dni) {
		citaDao.deleteAllByUsuarioDni(dni);
	}

=======
	@PostMapping("/marcarVacunado")
	public void marcarVacunado(HttpSession session, @RequestBody Map<String, Object> datosPaciente) {
		JSONObject jsonPaciente = new JSONObject(datosPaciente);

		String dni = jsonPaciente.optString("dni");
		String rol = jsonPaciente.optString("rol");

		Usuario usuarioVacunado = usuarioDao.findByDniAndRol(dni, rol);

		if (usuarioVacunado.getEstadoVacunacion().equals(EstadoVacunacion.NO_VACUNADO.name())) {
			usuarioDao.delete(usuarioVacunado);
			usuarioVacunado.setEstadoVacunacion(EstadoVacunacion.VACUNADO_PRIMERA.name());
			usuarioDao.save(usuarioVacunado);
		} else if (usuarioVacunado.getEstadoVacunacion().equals(EstadoVacunacion.VACUNADO_PRIMERA.name())) {
			usuarioDao.delete(usuarioVacunado);
			usuarioVacunado.setEstadoVacunacion(EstadoVacunacion.VACUNADO_SEGUNDA.name());
			usuarioDao.save(usuarioVacunado);
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getUsuarios")
	public List<Usuario> getUsuarios(HttpSession session) {
		return usuarioDao.findAll();
	}
>>>>>>> branch 'master' of https://SiGeVa@dev.azure.com/SiGeVa/SiGeVa/_git/SiGeVa
}

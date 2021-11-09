package edu.esi.uclm.http;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.RolUsuario;

@RestController
public class UsuarioController {

	@Autowired
	private CitaDao citaDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;

	@CrossOrigin(origins = "https://bold-spaceship-775486.postman.co")
	@PostMapping("/login")
	public void login(HttpServletRequest request, HttpSession session, @RequestBody Map<String, Object> datosUsuario) {
		try {
			JSONObject jso = new JSONObject(datosUsuario);
			String dni = jso.optString("dni");
			String password = jso.optString("password");
			String rol = jso.optString("rol");
			if (dni.length() == 0)
				throw new SigevaException(HttpStatus.FORBIDDEN, "Por favor, escribe tu DNI");

			Usuario usuario = usuarioDao.findByDniAndPasswordAndRol(dni, password, rol);
			if (usuario == null)
				throw new SigevaException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
			request.getSession().setAttribute("dniUsuario", dni);
			request.getSession().setAttribute("rolUsuario", rol);
		} catch (SigevaException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

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
			String rol = json.getString("rol");
			Usuario nuevoUsuario = new Usuario(dni, nombre, apellido, password, rol, centroSalud);
			comprobarCentro(centroSalud);
			nuevoUsuario.controlarContrasena();
			nuevoUsuario.comprobarDni();
			usuarioDao.save(nuevoUsuario);
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
				Optional<Usuario> recogido = usuarioDao.findById(user.getDni());
				if (!recogido.isPresent())
					throw new SiGeVaException(HttpStatus.NOT_FOUND, "No existe un usuario con este identificador");
				Usuario antiguoUsuario = recogido.get();
				antiguoUsuario.setNombre(user.getNombre());
				antiguoUsuario.setApellido(user.getApellido());
				if (!antiguoUsuario.getCentroSalud().equals(user.getCentroSalud()))
					comprobarCentro(user.getCentroSalud());
				antiguoUsuario.comprobarEstado();
				antiguoUsuario.setCentroSalud(user.getCentroSalud());
				antiguoUsuario.setPassword(user.getPassword());

				antiguoUsuario.controlarContrasena();
				usuarioDao.save(antiguoUsuario);
			}

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/eliminarUsuario")
	public void eliminarUsuario(@RequestBody Map<String, Object> datosUsuario) {
		try {

			JSONObject json = new JSONObject(datosUsuario);
			String dni = json.getString("dni");
			String rol = json.optString("rol");

			Usuario user = usuarioDao.findByDniAndRol(dni, rol);

			if (user == null)
				throw new SigevaException(HttpStatus.FORBIDDEN, "No existe este usuario");

			if (user.getRol().equals(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a otro administrador del sistema");

			if (user.getRol().equals("Paciente"))
				user.comprobarEstado();

			borrarCitas(dni);
			usuarioDao.delete(user);

			if (user.getRol().equals(RolUsuario.PACIENTE.name())
					&& !user.getEstadoVacunacion().equals(EstadoVacunacion.NO_VACUNADO.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a un paciente vacunado del sistema");

			borrarCitas(user.getDni());
			usuarioDao.delete(user);

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	private void borrarCitas(String dni) {
		citaDao.deleteByUsuarioDni(dni);

	}

	@PostMapping("/marcarVacunado")
	public void marcarVacunado(HttpSession session, @RequestBody Map<String, Object> datosPaciente) {
		JSONObject jsonPaciente = new JSONObject(datosPaciente);

		String dni = jsonPaciente.optString("dni");
		String rol = jsonPaciente.optString("rol");

		Usuario usuarioVacunado = usuarioDao.findByDniAndRol(dni, rol);
		CentroVacunacion centroVacunacion = centroVacunacionDao.findByNombre(usuarioVacunado.getCentroSalud());

		centroVacunacionDao.delete(centroVacunacion);
		centroVacunacion.setDosis(centroVacunacion.getDosis() - 1);

		centroVacunacionDao.save(centroVacunacion);

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

	private void comprobarCentro(String centroSalud) throws SiGeVaException {
		Optional<CentroVacunacion> centro = centroVacunacionDao.findById(centroSalud);
		if (centro.isPresent())
			throw new SiGeVaException(HttpStatus.NOT_FOUND, "No existe el centro de salud introducido");
	}

}

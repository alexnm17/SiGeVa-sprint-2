package edu.esi.uclm.http;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;
import edu.esi.uclm.utils.AuxiliaryMethods;

@RestController
public class UsuarioController {

	@Autowired
	private CitaDao citaDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;

	private static final String EMAIL = "email";
	private static final String NOMBRE = "nombre";
	private static final String PASSWORD = "password";

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/crearUsuario")
	public void crearUsuario(@RequestBody Map<String, Object> datosUsuario) {

		try {

			JSONObject json = new JSONObject(datosUsuario);
			String email = json.getString(EMAIL);
			String dni = json.getString("dni");
			AuxiliaryMethods.comprobarCampoVacio(dni);

			String nombre = json.getString(NOMBRE);
			AuxiliaryMethods.comprobarCampoVacio(nombre);

			String apellido = json.getString("apellido");
			AuxiliaryMethods.comprobarCampoVacio(apellido);

			String password = json.getString(PASSWORD);
			AuxiliaryMethods.comprobarCampoVacio(json.getString("centroSalud"));
			CentroVacunacion centroVacunacion = centroVacunacionDao.findByNombre(json.getString("centroSalud"));

			String rol = json.getString("rol");
			AuxiliaryMethods.comprobarCampoVacio(apellido);

			Usuario usuarioExistente = usuarioDao.findByEmail(email);

			if (usuarioExistente != null)
				throw new SigevaException(HttpStatus.ALREADY_REPORTED, "Ya existe un usuario con ese email");

			Usuario nuevoUsuario = new Usuario(email, dni, nombre, apellido, password, rol, centroVacunacion);

			AuxiliaryMethods.controlarContrasena(nuevoUsuario.getPassword());
			AuxiliaryMethods.comprobarEmail(nuevoUsuario.getEmail());
			AuxiliaryMethods.comprobarDni(nuevoUsuario.getDni());

			nuevoUsuario.setPassword(password);
			usuarioDao.save(nuevoUsuario);
		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/modificarUsuario")
	public void modificarUsuario(@RequestBody Map<String, Object> datosUsuario) {
		try {
			JSONObject json = new JSONObject(datosUsuario);
			String email = json.getString(EMAIL);
			String dni = json.getString("dni");
			AuxiliaryMethods.comprobarCampoVacio(dni);
			String nombre = json.getString(NOMBRE);
			AuxiliaryMethods.comprobarCampoVacio(nombre);
			String apellido = json.getString("apellido");
			AuxiliaryMethods.comprobarCampoVacio(apellido);
			String password = null;
			if (!json.optString(PASSWORD).isEmpty()) {
				password = json.getString(PASSWORD);
			}
			CentroVacunacion centroVacunacion = centroVacunacionDao.findByNombre(json.getJSONObject("centroVacunacion").getString(NOMBRE));
			String rol = json.getString("rol");
			Usuario antiguoUsuario = usuarioDao.findByEmail(email);

			if (antiguoUsuario == null)
				throw new SigevaException(HttpStatus.NOT_FOUND, "No existe un usuario con este identificador");
			else {
				Usuario user = new Usuario(email, dni, nombre, apellido, password, rol, centroVacunacion);
				if (antiguoUsuario.getRol().equalsIgnoreCase(RolUsuario.ADMINISTRADOR.name()))
					throw new SigevaException(HttpStatus.FORBIDDEN,
							"No puede modificar a otro administrador del sistema");

				AuxiliaryMethods.comprobarEmail(user.getEmail());
				AuxiliaryMethods.comprobarDni(user.getDni());

				antiguoUsuario.setNombre(user.getNombre());
				antiguoUsuario.setApellido(user.getApellido());
				antiguoUsuario.setDni(user.getDni());
				antiguoUsuario.setRol(user.getRol());
				antiguoUsuario.setNombre(user.getNombre());
				if (!antiguoUsuario.getCentroVacunacion().getIdCentroVacunacion().equals(user.getCentroVacunacion().getIdCentroVacunacion()))
					antiguoUsuario.comprobarEstado();
				antiguoUsuario.setCentroVacunacion(user.getCentroVacunacion());

				if (user.getPassword() != null) {
					AuxiliaryMethods.controlarContrasena(user.getPassword());
					antiguoUsuario.setPassword(user.getPassword());
				} else {
					antiguoUsuario.setPasswordModify(antiguoUsuario.getPassword());
				}

				usuarioDao.save(antiguoUsuario);
			}

		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/login")
	public String login(HttpServletRequest request, @RequestBody Map<String, Object> datosUsuario) {
		String rol = "";
		try {
			JSONObject jso = new JSONObject(datosUsuario);
			String email = jso.optString(EMAIL);
			String password = jso.optString(PASSWORD);
			if (email.length() == 0)
				throw new SigevaException(HttpStatus.FORBIDDEN, "Por favor, escribe tu Direccion de Correo");

			Usuario usuario = usuarioDao.findByEmailAndPassword(email, DigestUtils.sha512Hex(password));
			if (usuario == null)
				throw new SigevaException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
			request.getSession().setAttribute("emailUsuario", email);

			rol = usuario.getRol();
		} catch (SigevaException e) {
			if (e.getStatus() == HttpStatus.FORBIDDEN) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
			} else if (e.getStatus() == HttpStatus.UNAUTHORIZED) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
			}
		}
		return rol;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/eliminarUsuario")
	public void eliminarUsuario(@RequestBody Map<String, Object> datosUsuario) {
		try {

			JSONObject json = new JSONObject(datosUsuario);
			String email = json.getString(EMAIL);

			Usuario user = usuarioDao.findByEmail(email);

			if (user.getRol().equalsIgnoreCase(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a otro administrador del sistema");

			if (user.getRol().equalsIgnoreCase(RolUsuario.PACIENTE.name())
					&& !user.getEstadoVacunacion().equals(EstadoVacunacion.NO_VACUNADO.name()))
				throw new SigevaException(HttpStatus.LOCKED, "No puede eliminar a un paciente vacunado del sistema");

			borrarCitas(user);
			usuarioDao.delete(user);

		} catch (SigevaException e) {
				throw new ResponseStatusException(e.getStatus(), e.getMessage());
			
		}
	}

	private void borrarCitas(Usuario usuario) {
		citaDao.deleteAllByUsuario(usuario);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/marcarVacunado")
	public void marcarVacunado(HttpSession session, @RequestBody Map<String, Object> datosPaciente)
			throws SigevaException {

		try {
			JSONObject jsonPaciente = new JSONObject(datosPaciente);
			String email = jsonPaciente.getString(EMAIL);
			Usuario usuarioVacunado = usuarioDao.findByEmail(email);
			CentroVacunacion centroVacunacion = usuarioVacunado.getCentroVacunacion();

			String fechaHoy = LocalDate.now().toString();
			Cita citaDeEseDia = citaDao.findByUsuarioAndFecha(usuarioVacunado, fechaHoy);

			if (citaDeEseDia.getIsUsada())
				throw new SigevaException(HttpStatus.CONFLICT,
						"No se puede vacunar un paciente que ha sido vacunado hoy mismo");

			centroVacunacion.setDosis(centroVacunacion.getDosis() - 1);
			centroVacunacionDao.save(centroVacunacion);

			if (usuarioVacunado.getEstadoVacunacion().equals(EstadoVacunacion.NO_VACUNADO.name())) {
				usuarioVacunado.setEstadoVacunacion(EstadoVacunacion.VACUNADO_PRIMERA.name());
				usuarioDao.save(usuarioVacunado);

			} else if (usuarioVacunado.getEstadoVacunacion().equals(EstadoVacunacion.VACUNADO_PRIMERA.name())) {
				usuarioVacunado.setEstadoVacunacion(EstadoVacunacion.VACUNADO_SEGUNDA.name());
				usuarioDao.save(usuarioVacunado);
			}

			citaDeEseDia.setIsUsada(true);
			citaDao.save(citaDeEseDia);
		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getUsuarios")
	public List<Usuario> getUsuarios(HttpSession session) {
		return usuarioDao.findAll();
	}

}

package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;


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
	private UsuarioDao usuarioDao;
	@Autowired
	private CitaDao citaDao;
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;
	
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
			String estadoPaciente = EstadoVacunacion.NO_VACUNADO.name();
			String rol = json.getString("rol");
			Usuario nuevoUsuario = new Usuario(dni, nombre, apellido, password, rol, centroSalud, estadoPaciente);
			nuevoUsuario.controlarContraseña();
			nuevoUsuario.comprobarDni();
			usuarioDao.save(nuevoUsuario);
		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/modificarUsuario")
	public void modificarUsuario(@RequestBody Map<String, Object> datosUsuario) {
		try {
			JSONObject json = new JSONObject(datosUsuario);
			String email = json.getString(EMAIL);
			String dni = json.getString("dni");
			String nombre = json.getString("nombre");
			String apellido = json.getString("apellido");
			String password = json.getString("password");
			CentroVacunacion centroVacunacion = centroVacunacionDao.findByNombre(json.getJSONObject("centroVacunacion").getString("nombre"));
			String rol = json.getString("rol");
			
			Usuario user = new Usuario(email,dni, nombre, apellido, password, rol, centroVacunacion);
			
			if (user.getRol().equalsIgnoreCase(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede modificar a otro administrador del sistema");
			else {

				Usuario antiguoUsuario = usuarioDao.findByDni(user.getDni());

				if (antiguoUsuario == null)
					throw new SiGeVaException(HttpStatus.NOT_FOUND, "No existe un usuario con este identificador");
				antiguoUsuario.setNombre(user.getNombre());
				antiguoUsuario.setApellido(user.getApellido());
				antiguoUsuario.setDni(user.getDni());
				antiguoUsuario.setRol(user.getRol());
				antiguoUsuario.setNombre(user.getNombre());

				
				if (!antiguoUsuario.getCentroVacunacion().equals(user.getCentroVacunacion()))
					antiguoUsuario.comprobarEstado();
				antiguoUsuario.setCentroSalud(user.getCentroSalud());
				antiguoUsuario.setPassword(user.getPassword());

				antiguoUsuario.controlarContraseña();
				usuarioDao.save(antiguoUsuario);

			}

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	@PostMapping("/login")
    public String login(HttpServletRequest request, @RequestBody Map<String, Object> datosUsuario) {
        try {
            JSONObject jso = new JSONObject(datosUsuario);
            String email = jso.optString("email");
            String password= jso.optString("password");
            String rol= jso.optString("rol");
            if (dni.length()==0) throw new SigevaException(HttpStatus.FORBIDDEN, "Por favor, escribe tu DNI");
            
            Usuario usuario = usuarioDao.findByDniAndPasswordAndRol(dni, password, rol);
            if (usuario==null) throw new SigevaException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
            request.getSession().setAttribute("emailUsuario", email);
            
            return usuario.getRol();
        } catch (SigevaException e) {
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

			Usuario user = usuarioDao.findByDniAndRol(dni,rol);

			if (user.getRol().equals(RolUsuario.ADMINISTRADOR.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a otro administrador del sistema");

			if(user.getRol().equalsIgnoreCase(RolUsuario.PACIENTE.name()) && !user.getEstadoVacunacion().equals(EstadoVacunacion.NO_VACUNADO.name()))
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a un paciente vacunado del sistema");
			
			
			borrarCitas(user.getDni());
			usuarioDao.delete(user);
				
			
				
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	private void borrarCitas(String dni) {
		citaDao.deleteAllByUsuarioDni(dni);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/marcarVacunado")
	public void marcarVacunado(HttpSession session, @RequestBody Map<String, Object> datosPaciente) {
		JSONObject jsonPaciente = new JSONObject(datosPaciente);

		String dni = jsonPaciente.optString("dni");
		String rol = jsonPaciente.optString("rol");

		Usuario usuarioVacunado = usuarioDao.findByDniAndRol(dni, rol);
		CentroVacunacion centroVacunacion = centroVacunacionDao.findByNombre(usuarioVacunado.getCentroSalud());
		
		centroVacunacionDao.delete(centroVacunacion);
		centroVacunacion.setDosis(centroVacunacion.getDosis()-1);
		
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
}

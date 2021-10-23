package edu.esi.uclm.http;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioDao userDao;

	@PostMapping("/crearUsuario")
	public void CrearUsuario(@RequestBody Map<String, Object> datosUsuario) {
		JSONObject json = new JSONObject(datosUsuario);
		String dni = json.getString("dni");
		String nombre = json.getString("nombre");
		String apellido = json.getString("apellido");
		String password = json.getString("password");
		String centroSalud = json.getString("centroSalud");
		String rol = json.getString("rol");


		System.out.print("Hola, estoy creando al usuario:\t DNI: "+dni+" || Nombre: "+nombre);

		Usuario nuevoUsuario = new Usuario(dni,nombre,apellido,password, rol);
		userDao.save(nuevoUsuario);
	}


	@PostMapping("/modificarUsuario")
	public void ModificarUsuario(@RequestBody Usuario user) {
		try {
			if(user.getRol().equals(RolUsuario.ADMINISTRADOR)) throw new Exception("No puede modificar a otro administrador del sistema");


			else {
				System.out.print("Hola, estoy modificando al usuario:\t DNI: "+user.getDni()+" || Nombre: "+user.getNombre());
				Usuario antiguoUsuario = userDao.findByDni(user.getDni());
				userDao.deleteByDni(antiguoUsuario.getDni());
				antiguoUsuario.setNombre(user.getNombre());
				//cambiar todos los atributos


				System.out.print("Nuevos Datos son Nombre: "+antiguoUsuario.getNombre());
				userDao.save(antiguoUsuario);
			}

		}catch(Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

}

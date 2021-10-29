package edu.esi.uclm.http;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import edu.esi.uclm.model.Usuario;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.RolUsuario;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioDao userDao;

	@PostMapping("/crearUsuario")
	public void crearUsuario(@RequestBody Map<String, Object> datosUsuario) {
		JSONObject json = new JSONObject(datosUsuario);
		String dni = json.getString("dni");
		String nombre = json.getString("nombre");
		String apellido = json.getString("apellido");
		String password = json.getString("password");
		String centroSalud = json.getString("centroSalud");
		String rol = json.getString("rol");


		System.out.print("Hola, estoy creando al usuario:\t DNI: "+dni+" || Nombre: "+nombre);

		Usuario nuevoUsuario = new Usuario(dni,nombre,apellido, centroSalud, password, rol);
		userDao.save(nuevoUsuario);
	}


	@PostMapping("/modificarUsuario")
	public void modificarUsuario(@RequestBody Usuario user) {
		try {
			if(user.getRol().equals(RolUsuario.ADMINISTRADOR.name())) throw new SigevaException(HttpStatus.FORBIDDEN, "No puede modificar a otro administrador del sistema");


			else {
				System.out.print("Hola, estoy modificando al usuario:\t DNI: "+user.getDni()+" || Nombre: "+user.getNombre());
				Usuario antiguoUsuario = userDao.findByDni(user.getDni());
				antiguoUsuario.setNombre(user.getNombre());
				antiguoUsuario.setApellido(user.getApellido());
				antiguoUsuario.setCentro(user.getCentro());
				antiguoUsuario.setPassword(user.getPassword());
				
				userDao.save(antiguoUsuario);
			}

		}catch(Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	
	@DeleteMapping("/eliminarUsuario/{dni}")
	public void eliminarUsuario(@PathVariable String dni) {
		try {
			Usuario user = userDao.findByDni(dni);
			
			if(user.getRol().equals(RolUsuario.ADMINISTRADOR.name())) 
				throw new SigevaException(HttpStatus.FORBIDDEN, "No puede eliminar a otro administrador del sistema");
			else 
				userDao.delete(user);
			
		}catch(Exception e) {

			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}


}

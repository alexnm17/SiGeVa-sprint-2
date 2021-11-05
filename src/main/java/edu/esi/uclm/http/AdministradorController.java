/*package edu.esi.uclm.http;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import edu.esi.uclm.dao.PruebaDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.Usuario;

@RestController
public class AdministradorController {
	
	@PostMapping("/holaMundo")
	public void holaMundo(HttpSession session, @RequestBody Map<String, Object> datosPaciente) {
		JSONObject jso = new JSONObject(datosPaciente);
		String dni = jso.getString("dni");
		String nombre = jso.getString("nombre");
		System.out.println("Hola, "+ nombre+" con DNI:"+dni);	
		
		PruebaDao adminDao = new PruebaDao();
		adminDao.connection();
		adminDao.insertarPaciente(nombre, dni);
		adminDao.closeConnection();
		
	}

	

	
}
*/
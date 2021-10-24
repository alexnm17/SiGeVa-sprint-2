package edu.esi.uclm.http;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class centroVacunacionController {
	
	@PostMapping ("/crearCentro")
	public void addCentroVacunacion(HttpSession session, @RequestBody Map<String, Object> datoscentro) {
	
		
	}

}

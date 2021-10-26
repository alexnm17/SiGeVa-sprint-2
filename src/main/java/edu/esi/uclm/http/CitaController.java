package edu.esi.uclm.http;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.model.Cita;

public class CitaController {
	
	private CitaDao citadao;
	@PostMapping ("/solicitarCita")
	public void solicitarCita(HttpSession session) {
		
		
	}

	@PostMapping ("/modificarCita")
	public void modificarCita(HttpSession session, @RequestBody Map<String, Object> datosCita) {
		try {
			
			Optional<Cita> cita = citadao.findById("");
			cita.get().setFecha();
		}catch(Exception e) {
			
		}
		
		
		
	}
	
	@DeleteMapping ("/anularCita")
	public void anularCita(HttpSession session, @RequestBody String idCita) {
		citadao.deleteById(idCita);
	}
	
	@GetMapping ("/consultarCita")
	public void consultar(HttpSession session, @RequestBody String idCita) {
		citadao.findById(idCita);
	}
}

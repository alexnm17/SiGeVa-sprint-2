package edu.esi.uclm.http;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.model.FormatoVacunacion;

@RestController
public class FormatoVacunacionController {

	@Autowired
	private FormatoVacunacionDao formatoVacunacionDao;

	
	@PostMapping("/definirFormatoVacunacion")
	public void definirFormatoVacunacion(HttpSession session, @RequestBody Map<String, Object> datosFormatoVacunacion) {
		JSONObject jso = new JSONObject(datosFormatoVacunacion);
		
		String horaInicio = jso.getString("horaIncio");
		String horaFin = jso.getString("horaFin");
		int duracionFranja = jso.getInt("duracionFranja");
		int personasAVacunar = jso.getInt("personasAVacunar");
		
		FormatoVacunacion formatoVacunacion = new FormatoVacunacion(horaInicio, horaFin, duracionFranja, personasAVacunar);
		formatoVacunacionDao.insert(formatoVacunacion);
		
	}
	//ofhgiweurhwoehfoewhfpo
	
	@PostMapping ("/setPersonalVacunacion")
	public void setPersonalVacunacion() {
		
	}
	
	@PostMapping ("/setHorarioVacunacion")
	public void setHorarioVacunacion() {
	
	}
	
	@PostMapping ("/setDosisDisponibles")
	public void setDosisDisponibles() {
		
	}
	

}

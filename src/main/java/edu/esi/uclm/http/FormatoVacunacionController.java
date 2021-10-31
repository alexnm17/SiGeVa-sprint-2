package edu.esi.uclm.http;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.uclm.esi.exceptions.SiGeVaException;

@RestController
public class FormatoVacunacionController {

	@Autowired
	private FormatoVacunacionDao formatoVacunacionDao;

	
	@PostMapping("/definirFormatoVacunacion")
	public int definirFormatoVacunacion(HttpSession session, @RequestBody Map<String, Object> datosFormatoVacunacion) {
		int resultado = 0;
		
		try {
			JSONObject jso = new JSONObject(datosFormatoVacunacion);
			
			String horaInicio = jso.getString("horaInicio");
			String horaFin = jso.getString("horaFin");
			int duracionFranja = jso.getInt("duracionFranja");
			int personasAVacunar = jso.getInt("personasAVacunar");
			
			FormatoVacunacion formatoVacunacion = new FormatoVacunacion(horaInicio, horaFin, duracionFranja, personasAVacunar);
			if (formatoVacunacion.horasCorrectas() && formatoVacunacion.condicionesValidas()) {
				resultado = 200;
				formatoVacunacionDao.insert(formatoVacunacion);
				
			}else {
				if (!formatoVacunacion.horasCorrectas()) resultado = 409;
				else resultado = 410;
			}
		switch (resultado) {
		case 200:
			break;
		
		case 409:
			throw new SiGeVaException(HttpStatus.CONFLICT, "Las horas del formato no son correctas");

		case 410:
			throw new SiGeVaException(HttpStatus.CONFLICT, "Las condiciones no estan bien");
		
		default:
			throw new SiGeVaException(HttpStatus.CONFLICT, "Se ha alcanzado un caso no valido");
		}
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return resultado;
			
	}
	
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

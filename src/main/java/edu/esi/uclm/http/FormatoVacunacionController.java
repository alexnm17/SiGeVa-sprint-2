package edu.esi.uclm.http;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.FormatoVacunacion;

@RestController
public class FormatoVacunacionController {

	@Autowired
	private FormatoVacunacionDao formatoVacunacionDao;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/definirFormatoVacunacion")
	public void definirFormatoVacunacion(HttpSession session, @RequestBody Map<String, Object> datosFormatoVacunacion) {

		try {
			JSONObject jso = new JSONObject(datosFormatoVacunacion);
			String horaInicio = jso.getString("horaInicio");
			String horaFin = jso.getString("horaFin");
			int duracionFranja = jso.getInt("duracionFranja");
			int personasAVacunar = jso.getInt("personasAVacunar");

			FormatoVacunacion formatoVacunacion = new FormatoVacunacion(horaInicio, horaFin, duracionFranja,
					personasAVacunar);
			if (formatoVacunacion.horasCorrectas() /*&& formatoVacunacion.condicionesValidas()*/) {
				formatoVacunacionDao.insert(formatoVacunacion);
			} else {
				if (!formatoVacunacion.horasCorrectas())
					throw new SigevaException(HttpStatus.CONFLICT, "Las horas del formato no son correctas");
			/*	else
					throw new SigevaException(HttpStatus.CONFLICT, "Las condiciones no estan bien");*/
			}

		} catch (SigevaException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

}

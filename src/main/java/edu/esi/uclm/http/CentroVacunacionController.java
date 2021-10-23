package edu.esi.uclm.http;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;

@RestController
public class CentroVacunacionController {
	@Autowired
	private CentroVacunacionDao centroDao;
	
	public void crearCentroVacunacion(@RequestBody Map<String, Object> datosCentro) {
		JSONObject json = new JSONObject(datosCentro);
		String nombre = json.getString("nombre");
		String municipio = json.getString("municipio");
		
		CentroVacunacion centro = new CentroVacunacion(nombre, municipio);
		centroDao.save(centro);
	}

}

package edu.esi.uclm.http;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.uclm.esi.exceptions.SiGeVaException;


@RestController
public class CentroVacunacionController {
	
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;
	
	//Metodo para añadir centros de vacunacion a la BD
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/addCentro")
	public void darAltaCentroVacunacion(HttpServletRequest request,@RequestBody Map<String, Object> datosCentro) {
		try {

			JSONObject json = new JSONObject(datosCentro);
			String nombre = json.getString("nombre");
			String municipio = json.getString("municipio");
			int dosis = Integer.parseInt(json.getString("dosis"));
			
			CentroVacunacion centroVacunacion = new CentroVacunacion(nombre, municipio,dosis);
			centroVacunacionDao.save(centroVacunacion);
		} catch(Exception e) {	
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PostMapping("/modificarCentro")
	public void modificarCentro(@RequestBody Map<String, Object> datosCentro) {
		try {

			JSONObject json = new JSONObject(datosCentro);
			String nombre = json.getString("nombre");
			String municipio = json.getString("municipio");
			int dosis = Integer.parseInt(json.getString("dosis"));
			
			CentroVacunacion antiguoCentro = centroVacunacionDao.findById(nombre).get();

			if (antiguoCentro == null)
				throw new SiGeVaException(HttpStatus.NOT_FOUND, "No existe un centro con este nombre");
			
			antiguoCentro.setMunicipio(municipio);
			antiguoCentro.setDosis(dosis);
			
			centroVacunacionDao.save(antiguoCentro);
		} catch (SiGeVaException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

	}

	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getAllCentros")
	public List<CentroVacunacion> getAllCentros(HttpServletRequest request){
		return centroVacunacionDao.findAll();
	}

}

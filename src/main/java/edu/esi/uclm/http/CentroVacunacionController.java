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
import edu.esi.uclm.exceptions.SigevaException;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.utils.AuxiliaryMethods;

@RestController
public class CentroVacunacionController {

	@Autowired
	private CentroVacunacionDao centroVacunacionDao;

	private static final String DOSIS = "dosis";

	// Metodo para añadir centros de vacunacion a la BD
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/addCentro")
	public void darAltaCentroVacunacion(HttpServletRequest request, @RequestBody Map<String, Object> datosCentro) {
		try {

			JSONObject json = new JSONObject(datosCentro);
			String nombre = json.getString("nombre");
			AuxiliaryMethods.comprobarCampoVacio(nombre);

			String municipio = json.getString("municipio");
			AuxiliaryMethods.comprobarCampoVacio(municipio);

			AuxiliaryMethods.comprobarCampoVacio(json.getString(DOSIS));
			int dosis = Integer.parseInt(json.getString(DOSIS));

			CentroVacunacion centroVacunacion = new CentroVacunacion(nombre, municipio, dosis);
			if (centroVacunacionDao.findByNombre(nombre) != null)
				throw new SigevaException(HttpStatus.CONFLICT, "No se puede crear el centro puesto que ya existe");

			centroVacunacionDao.save(centroVacunacion);
		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/modificarCentro")
	public void modificarCentro(@RequestBody Map<String, Object> datosCentro) {
		try {
			JSONObject json = new JSONObject(datosCentro);
			String idCentroVacunacion = json.getString("idCentroVacunacion");
			String nombre = json.getString("nombre");
			AuxiliaryMethods.comprobarCampoVacio(nombre);

			String municipio = json.getString("municipio");
			AuxiliaryMethods.comprobarCampoVacio(municipio);

			//Esta lógica compruba que si desde del front se manda un mensaje con las dosis vacías, nos de un error.
			int dosis = -1;
			if(json.get(DOSIS).equals("")) {
				throw new SigevaException(HttpStatus.NOT_IMPLEMENTED,"El campo esta vacio.");
			} else {
				dosis = json.getInt(DOSIS);
			}

			CentroVacunacion antiguoCentro = centroVacunacionDao.findByIdCentroVacunacion(idCentroVacunacion);

			if (antiguoCentro == null)
				throw new SigevaException(HttpStatus.NOT_FOUND, "No existe un centro con este nombre");

			antiguoCentro.setNombre(nombre);
			antiguoCentro.setMunicipio(municipio);
			antiguoCentro.setDosis(dosis);

			centroVacunacionDao.save(antiguoCentro);
		} catch (SigevaException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getAllCentros")
	public List<CentroVacunacion> getAllCentros(HttpServletRequest request) {
		return centroVacunacionDao.findAll();
	}

}

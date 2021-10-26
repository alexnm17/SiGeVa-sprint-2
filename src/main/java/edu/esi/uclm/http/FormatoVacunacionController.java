package edu.esi.uclm.http;


import java.util.Map;

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


	private FormatoVacunacionDao formatovacunaciondao;
	
	@PostMapping ("/Generar Formato")
	public void generarFormatoVacunacion(@RequestBody FormatoVacunacion formato) {
		
		try {
			if (formato.horasCorrectas() || formato.getPersonasPorFranja()>1) 
				throw new SiGeVaException(HttpStatus.FORBIDDEN,"Valores no permitidos para el formato");
				
			formatovacunaciondao.save(formato);
			
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}

		
	}
	@PostMapping ("/")
	public void setPersonalVacunacion() {
		
	}

	@PostMapping ("/")
	public void setHorarioVacunacion() {
	
	}

	
	@PostMapping ("/")
	public void setDosisDisponibles() {
		
	}
	
}

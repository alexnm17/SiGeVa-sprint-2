package edu.esi.uclm.http;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListaVacunacionController {

	@GetMapping
	public void consultarLista(Map <String, Object> datos) {
		
	}
	
	@PutMapping
	public void setVacunado() {

	}
	
}

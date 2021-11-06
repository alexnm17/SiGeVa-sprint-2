package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.uclm.esi.exceptions.SiGeVaException;

class TestFormatoVacunacionControllerHorasIncorrectas {

	private FormatoVacunacionDao dao;
	private FormatoVacunacionController componente;
	
	@Test
	void test() {
		componente = new FormatoVacunacionController();
		Map<String, Object> body = new HashMap<String,Object>();
		body.put("horaInicio","12:30");
		body.put("horaFin","11:00");
		body.put("duracionFranja",10);
		body.put("personasAVacunar",1);
		
		Exception e =assertThrows(ResponseStatusException.class,() ->componente.definirFormatoVacunacion(null,body));
		
		String resultado = e.getMessage();
		String esperado = "409 CONFLICT \"Las horas del formato no son correctas\"";
		assertEquals(esperado,resultado);
	}

}

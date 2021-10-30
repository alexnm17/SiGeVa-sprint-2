package edu.esi.uclm.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
public class TestFormatoVacunacionControler extends TestCase{

	private FormatoVacunacionController componente;
	
	@Test
	public void testDefinirFormatoCorrecto() {
		
		componente = new FormatoVacunacionController();
		Map<String, Object> body = new HashMap<String,Object>();
		body.put("horaInicio","12:30");
		body.put("horaFin","18:00");
		body.put("duracionFranja",1);
		body.put("personasAVacunar",10);
		int resultado = componente.definirFormatoVacunacion(null,body);
		Assert.assertEquals(200,resultado);
		
	}
	
	
	
}

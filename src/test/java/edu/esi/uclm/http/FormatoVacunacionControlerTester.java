package edu.esi.uclm.http;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import edu.esi.uclm.model.FormatoVacunacion;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)

public class FormatoVacunacionControlerTester extends TestCase{

	private FormatoVacunacionController componente;
	
	@Test
	public void testGenerarCorrecto() {
		
		componente = new FormatoVacunacionController();
		Map<String, Object> body;
		body.put("horaInicio","12:30");
		body.put("horaFin","18:00");
		body.put("duracionFranja","");
		body.put("personasAVacunar",10);
		int resultado = componente.definirFormatoVacunacion(null,body);
		Assert.assertVoid(componente.definirFormatoVacunacion(null,body));
		
		
	}
	
	
	
}

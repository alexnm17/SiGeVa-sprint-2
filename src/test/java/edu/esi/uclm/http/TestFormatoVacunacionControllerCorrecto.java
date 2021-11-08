package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.model.FormatoVacunacion;

class TestFormatoVacunacionControllerCorrecto {

	private FormatoVacunacionDao dao;
	private FormatoVacunacionController componente;

	@Test
	void test() {
		componente = new FormatoVacunacionController();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("horaInicio", "12:30");
		body.put("horaFin", "18:00");
		body.put("duracionFranja", 10);
		body.put("personasAVacunar", 1);
		FormatoVacunacion f = new FormatoVacunacion("12:30", "18:00", 10, 1);

		componente.definirFormatoVacunacion(null, body);

		List<FormatoVacunacion> resultado = dao.findAll();
		Assert.assertEquals(f, resultado.get(0));

	}

}

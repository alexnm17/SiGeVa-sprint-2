package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class TestCitaControllerNoExiste {

	@Test
	void test() {
		CitaController citaController = new CitaController();
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("dni", "NoExiste");
		Exception e = assertThrows(ResponseStatusException.class,()->citaController.solicitarCita(null,mapa));
		String esperado = "409 CONFLICT No se ha encontrado ningun usuario con este dni";
		assertEquals(esperado, e.getMessage());
	}

}

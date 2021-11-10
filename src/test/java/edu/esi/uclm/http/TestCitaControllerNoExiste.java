package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.server.ResponseStatusException;

@DataMongoTest
class TestCitaControllerNoExiste {

	@Test
	public void test() {
		CitaController citaController = new CitaController();
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("dni", "NoExiste");
		
		HttpSession session = mock(HttpSession.class);
		session.setAttribute("dni","");
		Exception e = assertThrows(ResponseStatusException.class,()->citaController.solicitarCita(session,mapa));
		String esperado = "409 CONFLICT No se ha encontrado ningun usuario con este dni";
		assertEquals(esperado, e.getMessage());
	}

}

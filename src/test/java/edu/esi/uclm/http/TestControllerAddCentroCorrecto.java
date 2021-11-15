package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataMongoTest
class TestControllerAddCentroCorrecto {



	@Mock
	CentroVacunacionDao dao;
	
	@InjectMocks
	private CentroVacunacionController centroVacunacion;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("nombre", "pruebaCemtro");
		mapa.put("municipio", "pruebamunicipio");
		mapa.put("dosis", "1000");
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
	}
	@Test
	public void test() {

		
		
		CentroVacunacion centro = new CentroVacunacion("pruebaCemtro", "pruebamunicipio", 1000);
		CentroVacunacion resultado = dao.findByNombre("pruebaCemtro");
		assertEquals(centro,resultado);
	}

}

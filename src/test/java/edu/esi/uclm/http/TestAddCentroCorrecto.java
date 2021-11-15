package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.uclm.esi.exceptions.SiGeVaException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class TestAddCentroCorrecto {

	private MockMvc mockMvc;

	@InjectMocks
	private CentroVacunacionController centroVacunacionController;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(centroVacunacionController).build();
	}
	
	@Mock
	CentroVacunacionDao dao;

	@Test
	void test() throws Exception {
		CentroVacunacionDao mockdao = mock(CentroVacunacionDao.class);
		CentroVacunacion centro = new CentroVacunacion("pruebaCemtro", "pruebamunicipio", 1000);
		try {
			
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("nombre", "pruebaCemtro");
		mapa.put("municipio", "pruebamunicipio");
		mapa.put("dosis", "1000");
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		when(mockdao.save(centro)).thenThrow(new SiGeVaException(null,"Esta tarea se ha realizado correctamente"));
		mockMvc.perform(MockMvcRequestBuilders.post("/addCentro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(MockMvcResultMatchers.status().isOk());
		}catch(Exception e) {
			assertEquals("Esta tarea se ha realizado correctamente", e.getMessage());
		}

	}

}

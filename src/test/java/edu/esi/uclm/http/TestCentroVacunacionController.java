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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class TestCentroVacunacionController {

	private MockMvc mockMvc;

	@InjectMocks
	private CentroVacunacionController centroVacunacionController;
	
	@Mock
	CentroVacunacionDao centroVacunacionDao;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(centroVacunacionController).build();
	}

	@Test
	void testCrearCentroCorrecto() {
			
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("nombre", "pruebaCentro");
		mapa.put("municipio", "pruebamunicipio");
		mapa.put("dosis", "1000");
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		//CentroVacunacion centroRaro = doThrow(new Exception()).when(centroVacunacionDao.save(any(CentroVacunacion.class)));
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/addCentro")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
					.andExpect(MockMvcResultMatchers.status().isOk());
			
			System.out.println("Centro Creado");
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}
	
	
	@Test
	void testCrearCentroError() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("nombre", "Alarcos");
		mapa.put("municipio", "Ciudad Real");
		mapa.put("dosis", "3000");
		
		CentroVacunacion centro = new CentroVacunacion("Alarcos","Ciudad Real",3000);
		
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		try {
			when(centroVacunacionDao.findByNombre(any())).thenReturn(centro);
			
			mockMvc.perform(MockMvcRequestBuilders.post("/addCentro")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
					.andExpect(MockMvcResultMatchers.status().isConflict());
			assertTrue(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		
		}
		
	}
	
	@Test
	void testModificarCentroCorrecto() {
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("nombre", "Alarcos");
		mapa.put("municipio", "Ciudad real");
		mapa.put("dosis", "3000");
		
		CentroVacunacion centro = new CentroVacunacion("Alarcos","Ciudad real",3000);
		
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		try {
			when(centroVacunacionDao.findByNombre(any())).thenReturn(centro);
			mockMvc.perform(MockMvcRequestBuilders.post("/modificarCentro")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
					.andExpect(MockMvcResultMatchers.status().isOk());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test
	void testModificarCentroError(){
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("nombre", "El bombo");
		mapa.put("municipio", "Tomelloso");
		mapa.put("dosis", "3000");
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		try {
			when(centroVacunacionDao.findByNombre(any())).thenReturn(null);
			mockMvc.perform(MockMvcRequestBuilders.post("/modificarCentro")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
					.andExpect(MockMvcResultMatchers.status().isConflict());
			assertTrue(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		
		
		}
		
	}
	
	
	@Test
	void testGetAllCentros(){
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/getAllCentros"))
			.andExpect(MockMvcResultMatchers.status().isOk());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	

}

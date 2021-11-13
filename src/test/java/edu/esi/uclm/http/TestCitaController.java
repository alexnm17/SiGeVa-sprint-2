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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.esi.uclm.dao.CentroVacunacionDao;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class TestCitaController {
	
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
	void testSolicitarCitaCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "rafa@gmail.com");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/solicitarCita")
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
	void testModificarCitaCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("emailUsuario", "rafa@gmail.com");
		mapa.put("hora","8:00:00");
		mapa.put("fecha","01-12-2021");
		mapa.put("idCita", "");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/modificarCita")
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
	void testAnularCitaCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idCita", "");
		
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete("/anularCita")
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
	void testCrearPlantillasVacunacion() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/crearPlantillasCitaVacunacion"))
			.andExpect(MockMvcResultMatchers.status().isOk());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	void testGetFormatoVacunacion() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/getFormatoVacunacion"))
			.andExpect(MockMvcResultMatchers.status().isOk());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	void testGetCitasHoy() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("emailUsuario", "rafa@gmail.com");
		mapa.put("fecha", "01-12-2021");
		
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/getCitasHoy"))
			.andExpect(MockMvcResultMatchers.status().isOk());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

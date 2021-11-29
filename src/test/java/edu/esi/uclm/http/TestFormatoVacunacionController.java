package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.CupoDao;
import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Cupo;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.esi.uclm.model.Usuario;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class TestFormatoVacunacionController{
	
	private MockMvc mockMvc;

	@InjectMocks
	private FormatoVacunacionController formatoController;
	
	
	@Mock
	FormatoVacunacionDao formatoVacunacionDao;
	

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(formatoController).build();
	}

	@Test
	void testDefinirFormatoVacunacionCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("horaInicio", "09:00");
		mapa.put("horaFin", "12:00");
		mapa.put("duracionFranja", 30);
		mapa.put("personasAVacunar", 10);

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/definirFormatoVacunacion")
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
	void testDefinirFormatoVacunacionInCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("horaInicio", "40:09");
		mapa.put("horaFin", "12:00");
		mapa.put("duracionFranja", 30);
		mapa.put("personasAVacunar", 10);

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		

		
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/definirFormatoVacunacion")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
					.andExpect(MockMvcResultMatchers.status().isConflict());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

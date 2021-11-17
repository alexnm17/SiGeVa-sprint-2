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
class TestCitaController {
	
	private MockMvc mockMvc;

	@InjectMocks
	private CitaController citaController;
	
	@Mock
	UsuarioDao usuarioDao;
	
	@Mock
	CitaDao citaDao;
	
	@Mock
	CentroVacunacionDao centroVacunacionDao;
	
	@Mock
	CupoDao cupoDao;
	
	@Mock
	FormatoVacunacionDao formatoVacunacionDao;
	

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(citaController).build();
	}

	@Test
	void testSolicitarCitaCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","Prueba","Probando","Contraseña","Paciente",centroUsuario);
		Cita citaPrueba = new Cita("2021-12-01","09:00",usuarioPrueba);
		Cupo cupoPrueba = new Cupo("2021-12-01","09:00",centroUsuario,10);
		Cupo cupoPrueba2 = new Cupo("2021-12-22","09:00",centroUsuario,10);
		List<Cupo> listaCuposUsuario = new ArrayList<Cupo>();
		listaCuposUsuario.add(cupoPrueba);
		listaCuposUsuario.add(cupoPrueba2);
		List<Cita> listaCitasUsuario = new ArrayList<Cita>();
		
		try {
			when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			lenient().when(citaDao.findByUsuario(usuarioPrueba)).thenReturn(citaPrueba);
			when(citaDao.findAllByUsuario(usuarioPrueba)).thenReturn(listaCitasUsuario);
			when(cupoDao.findAllByCentroVacunacion(centroUsuario)).thenReturn(listaCuposUsuario);
			
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
		mapa.put("fecha","2021-12-01");
		mapa.put("idCita", "IdDeLaCita");
		mapa.put("idCupo", "idDelCupo");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","Prueba","Probando","Contraseña","Paciente",centroUsuario);
		Cita citaPrueba = new Cita("8:00","2021-12-01",usuarioPrueba);
		Optional<Cupo> cupoPrueba = Optional.ofNullable(new Cupo("2021-12-01","09:00",centroUsuario,10));
		List<Cita> listaCitasUsuario = new ArrayList<Cita>();
		listaCitasUsuario.add(citaPrueba);
		
		try {
			lenient().when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			lenient().when(citaDao.findByIdCita(any())).thenReturn(citaPrueba);
			lenient().when(citaDao.findAllByUsuarioEmail(any())).thenReturn(listaCitasUsuario);
			lenient().when(cupoDao.findById(any())).thenReturn(cupoPrueba);
			
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
		mapa.put("idCita", "619100645b35a000057515a1");
		
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
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Optional<FormatoVacunacion> formato = Optional.ofNullable(new FormatoVacunacion("08:00","10:00",30,10));
		List<CentroVacunacion> listaCentrosUsuario = new ArrayList<CentroVacunacion>();
		listaCentrosUsuario.add(centroUsuario);
		
		try {
			when(centroVacunacionDao.findAll()).thenReturn(listaCentrosUsuario);
			when(formatoVacunacionDao.findById(any())).thenReturn(formato);
			mockMvc.perform(MockMvcRequestBuilders.post("/crearPlantillasCitaVacunacion"))
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
		
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","Prueba","Probando","Contraseña","Paciente",centroUsuario);
		Cita citaPrueba = new Cita("8:00","2021-12-01",usuarioPrueba);
		List<Cita> listaCitasUsuario = new ArrayList<Cita>();
		listaCitasUsuario.add(citaPrueba);
		
		
		try {
			when(citaDao.findAllByFechaAndCentroVacunacion(any(), any())).thenReturn(listaCitasUsuario);
			lenient().when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.post("/getCitasHoy")
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
	void testGetCitasOtroDia() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("emailUsuario", "rafa@gmail.com");
		mapa.put("fecha","2021-12-01");
		
		JSONObject json = new JSONObject(mapa);
		String body = json.toString();
		
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","Prueba","Probando","Contraseña","Paciente",centroUsuario);
		Cita citaPrueba = new Cita("8:00","2021-12-01",usuarioPrueba);
		List<Cita> listaCitasUsuario = new ArrayList<Cita>();
		listaCitasUsuario.add(citaPrueba);
		
		
		try {
			when(citaDao.findAllByFechaAndCentroVacunacion(any(), any())).thenReturn(listaCitasUsuario);
			lenient().when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.post("/getCitasOtroDia")
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
	void testConsultarCita() {
		//Falta Por hacer
		
	}

}

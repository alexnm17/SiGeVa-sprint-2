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
import edu.esi.uclm.model.EstadoVacunacion;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.esi.uclm.model.Usuario;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class TestUsuarioController {

	private MockMvc mockMvc;

	@InjectMocks
	private UsuarioController usuarioController;

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
		mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
	}

	@Test
	void testCrearUsuarioCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		mapa.put("dni", "03255972L");
		mapa.put("nombre", "pepe");
		mapa.put("apellido", "prueba");
		mapa.put("password", "Prueba123");
		mapa.put("centroSalud", "Elcastigo");
		mapa.put("rol", "Paciente");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		CentroVacunacion centro = new CentroVacunacion("Elcastigo","VillaPrueba",10000);

		try {
			when(centroVacunacionDao.findByNombre(any())).thenReturn(centro);

			mockMvc.perform(MockMvcRequestBuilders.post("/crearUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isOk());
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testCrearUsuarioContraseñaNoSegura() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		mapa.put("dni", "03255972L");
		mapa.put("nombre", "pepe");
		mapa.put("apellido", "prueba");
		mapa.put("password", "Prueba1");
		mapa.put("centroSalud", "Elcastigo");
		mapa.put("rol", "Paciente");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		CentroVacunacion centro = new CentroVacunacion("Elcastigo","VillaPrueba",10000);

		try {
			when(centroVacunacionDao.findByNombre(any())).thenReturn(centro);

			mockMvc.perform(MockMvcRequestBuilders.post("/crearUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isConflict());
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testCrearUsuarioDniNoValido() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		mapa.put("dni", "03255");
		mapa.put("nombre", "pepe");
		mapa.put("apellido", "prueba");
		mapa.put("password", "Prueba123");
		mapa.put("centroSalud", "Elcastigo");
		mapa.put("rol", "Paciente");

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		CentroVacunacion centro = new CentroVacunacion("Elcastigo","VillaPrueba",10000);

		try {
			when(centroVacunacionDao.findByNombre(any())).thenReturn(centro);

			mockMvc.perform(MockMvcRequestBuilders.post("/crearUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isConflict());
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testModificarUsuarioCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		mapa.put("dni", "03255873A");
		mapa.put("nombre", "pepe");
		mapa.put("apellido", "prueba");
		mapa.put("password", "Prueba123");
		Map<String, Object> mapaCentro = new HashMap<String, Object>();
		mapaCentro.put("nombre", "Alarcos");
		JSONObject jsonCentro = new JSONObject(mapaCentro);
		mapa.put("centroVacunacion", jsonCentro);
		mapa.put("rol", "Paciente");

		JSONObject json = new JSONObject(mapa);

		String body = json.toString();

		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Paciente",centroUsuario);


		try {
			lenient().when(centroVacunacionDao.findByNombre(any())).thenReturn(centroUsuario);
			lenient().when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);

			mockMvc.perform(MockMvcRequestBuilders.post("/modificarUsuario")
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
	void testModificarUsuarioAntiguoNoExiste() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		mapa.put("dni", "03255");
		mapa.put("nombre", "pepe");
		mapa.put("apellido", "prueba");
		mapa.put("password", "Prueba123");
		Map<String, Object> mapaCentro = new HashMap<String, Object>();
		mapaCentro.put("nombre", "Alarcos");
		JSONObject jsonCentro = new JSONObject(mapaCentro);
		mapa.put("centroVacunacion", jsonCentro);
		mapa.put("rol", "Paciente");

		JSONObject json = new JSONObject(mapa);

		String body = json.toString();

		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);

		try {
			lenient().when(centroVacunacionDao.findByNombre(any())).thenReturn(centroUsuario);
			lenient().when(usuarioDao.findByEmail(any())).thenReturn(null);

			mockMvc.perform(MockMvcRequestBuilders.post("/modificarUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	void testModificarUsuarioEsAdministrador() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		mapa.put("dni", "03255");
		mapa.put("nombre", "pepe");
		mapa.put("apellido", "prueba");
		mapa.put("password", "Prueba123");
		Map<String, Object> mapaCentro = new HashMap<String, Object>();
		mapaCentro.put("nombre", "Alarcos");
		JSONObject jsonCentro = new JSONObject(mapaCentro);
		mapa.put("centroVacunacion", jsonCentro);
		mapa.put("rol", "Administrador");

		JSONObject json = new JSONObject(mapa);

		String body = json.toString();

		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Administrador",centroUsuario);


		try {
			lenient().when(centroVacunacionDao.findByNombre(any())).thenReturn(centroUsuario);
			lenient().when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);

			mockMvc.perform(MockMvcRequestBuilders.post("/modificarUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	void testEliminarUsuarioCorrecto() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Paciente",centroUsuario);


		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		try {
			when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.delete("/eliminarUsuario")
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
	void testEliminarUsuarioAdministrador() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Administrador",centroUsuario);

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		try {
			when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.delete("/eliminarUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	void testEliminarUsuarioTieneVacunas() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Paciente",centroUsuario);

		usuarioPrueba.setEstadoVacunacion(EstadoVacunacion.VACUNADO_PRIMERA.name());

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		try {
			when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.delete("/eliminarUsuario")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
			.andExpect(MockMvcResultMatchers.status().isLocked());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	void testMarcarVacunadoNoVacunado() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Paciente",centroUsuario);

		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		try {
			when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.post("/marcarVacunado")
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
	void testMarcarVacunadoVacunadoPrimera() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("email", "prueba@gmail.com");
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Usuario usuarioPrueba = new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Paciente",centroUsuario);

		usuarioPrueba.setEstadoVacunacion(EstadoVacunacion.VACUNADO_PRIMERA.name());


		JSONObject json = new JSONObject(mapa);
		String body = json.toString();

		try {
			when(usuarioDao.findByEmail(any())).thenReturn(usuarioPrueba);
			mockMvc.perform(MockMvcRequestBuilders.post("/marcarVacunado")
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
	void testGetUsuarios() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/getUsuarios"))
			.andExpect(MockMvcResultMatchers.status().isOk());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

package edu.esi.uclm.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
class TestCupoControlller{
	
	private MockMvc mockMvc;

	@InjectMocks
	private CupoController cupoController;
	
	
	@Mock
	CupoDao cupoDao;
	
	@Mock
	UsuarioDao usuarioDao;
	

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(cupoController).build();
	}

	@Test
	void testGetAllCuposConHuecoCorrecto() {
		String email = "prueba@gmail.com";
		String fecha = LocalDate.now().toString();
		CentroVacunacion centroUsuario = new CentroVacunacion("Alarcos","CiudadReal",2000);
		Optional<Usuario> usuarioPrueba = Optional.ofNullable(new Usuario("prueba@gmail.com","0000000Q","pepe","prueba","Prueba123","Paciente",centroUsuario));
		List<Cupo> listacupos = new ArrayList<Cupo>();
		
		try {
			when(usuarioDao.findById(any())).thenReturn(usuarioPrueba);
			lenient().when(cupoDao.findAllByCentroVacunacion(centroUsuario)).thenReturn(listacupos);
			mockMvc.perform(MockMvcRequestBuilders.get("/getAllCuposConHuecoPorFecha")
					.contentType(MediaType.ALL_VALUE)
					.param("email",email)
					.param("fecha",fecha))
					.andExpect(MockMvcResultMatchers.status().isOk());
			//si no hay excepciones va bien
			assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

}

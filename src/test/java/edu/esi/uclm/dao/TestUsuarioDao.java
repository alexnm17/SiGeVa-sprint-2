package edu.esi.uclm.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

class TestUsuarioDao {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testfindById() {
		UsuarioDao userdao = mock(UsuarioDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		when(userdao.findByDni(anyString())).thenReturn(usuario);
		Usuario resultado = userdao.findByDni("prueba");
		assertEquals(usuario, resultado);
	}
	
	@Test
	void testfindByEmail() {
		UsuarioDao userdao = mock(UsuarioDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		when(userdao.findByEmail(anyString())).thenReturn(usuario);
		Usuario resultado = userdao.findByEmail("prueba");
		assertEquals(usuario, resultado);
	}

	@Test
	void testfindByEmailAndPassword() {
		UsuarioDao userdao = mock(UsuarioDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		when(userdao.findByEmailAndPassword(anyString(),anyString())).thenReturn(usuario);
		Usuario resultado = userdao.findByEmailAndPassword("prueba@email.com","pruebaApellido");
		assertEquals(usuario, resultado);
	}
}

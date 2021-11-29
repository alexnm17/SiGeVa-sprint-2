package edu.esi.uclm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

class TestCitaDao {

	@BeforeEach
	void setUp() throws Exception {
	}

	

	@Test
	void testfindByFechaAndHora() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		Cita cita = new Cita(LocalDate.now().toString(), LocalTime.now().toString(),  usuario);
		when(citadao.findByFechaAndHora(anyString(), anyString())).thenReturn(cita);
		LocalTime time = LocalTime.now();
		LocalDate date = LocalDate.now();

		Cita resultado = citadao.findByFechaAndHora(date.toString(), time.toString());
		assertEquals(cita, resultado);
	}

	@Test
	void testdeleteByFechaAndHora() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		((CitaDao) doNothing().when(citadao)).deleteByFechaAndHora(anyString(), anyString());
		LocalTime time = LocalTime.now();
		LocalDate date = LocalDate.now();
		citadao.deleteByFechaAndHora(date.toString(), time.toString());
	}

	@Test
	void testfindAllByFechaAndCentroVacunacion() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		Cita cita = new Cita(LocalDate.now().toString(), LocalTime.now().toString(),  usuario);
		List<Cita> lista = new ArrayList<Cita>();
		lista.add(cita);
		when(citadao.findAllByFechaAndCentroVacunacion(anyString(), any())).thenReturn(lista);
		LocalDate date = LocalDate.now();
		List<Cita> resultado = citadao.findAllByFechaAndCentroVacunacion(date.toString(), centro);
		assertEquals(cita, resultado.get(0));
	}

	@Test
	void testfindAllByUsuario() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		Cita cita = new Cita(LocalDate.now().toString(), LocalTime.now().toString(),  usuario);
		List<Cita> lista = new ArrayList<Cita>();
		lista.add(cita);
		when(citadao.findAllByUsuario(any())).thenReturn(lista);
		List<Cita> resultado = citadao.findAllByUsuario(usuario);
		assertEquals(cita, resultado.get(0));
	}

	@Test
	void testfindByUsuario() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		Cita cita = new Cita(LocalDate.now().toString(), LocalTime.now().toString(),  usuario);
		when(citadao.findByUsuario(any())).thenReturn(cita);
		Cita resultado = citadao.findByUsuario(usuario);
		assertEquals(cita, resultado);
	}

	@Test
	void testdeleteAllByUsuario() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);

		((CitaDao) doNothing().when(citadao)).deleteAllByUsuario(usuario);

		citadao.deleteAllByUsuario(usuario);
		verify(citadao,times(1)).deleteAllByUsuario(usuario);;
	}

	@Test
	void testfindAllByUsuarioEmail() {

		CitaDao citadao = mock(CitaDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Usuario usuario = new Usuario("prueba@email.com", "DniPrueba", "pruebaNombre", "pruebaApellido",
				"pruebaPasword", RolUsuario.PACIENTE.name(), centro);
		Cita cita = new Cita(LocalDate.now().toString(), LocalTime.now().toString(),  usuario);
		List<Cita> lista = new ArrayList<Cita>();
		lista.add(cita);
		when(citadao.findAllByUsuarioEmail(anyString())).thenReturn(lista);
		List<Cita> resultado = citadao.findAllByUsuarioEmail(usuario.getEmail());
		assertEquals(cita, resultado.get(0));
	}

}

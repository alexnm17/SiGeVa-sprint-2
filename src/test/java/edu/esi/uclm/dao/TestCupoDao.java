package edu.esi.uclm.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Cupo;
import edu.esi.uclm.model.RolUsuario;
import edu.esi.uclm.model.Usuario;

class TestCupoDao {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testfindAllByCentroVacunacion() {

		CupoDao cupodao = mock(CupoDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Cupo cupo = new Cupo(LocalDate.now().toString(), LocalTime.now().toString(), centro, 10);
		List<Cupo> lista = new ArrayList<Cupo>();
		lista.add(cupo);
		when(cupodao.findAllByCentroVacunacion(any())).thenReturn(lista);
		List<Cupo> resultado = cupodao.findAllByCentroVacunacion(centro);
		assertEquals(cupo, resultado.get(0));
	}

	@Test
	void testfindByFechaAndHora() {
		CupoDao cupodao = mock(CupoDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		Cupo cupo = new Cupo(LocalDate.now().toString(), LocalTime.now().toString(), centro, 10);
		
		when(cupodao.findByFechaAndHora(anyString(),anyString())).thenReturn(cupo);
		Cupo resultado = cupodao.findByFechaAndHora(LocalDate.now().toString(),LocalTime.now().toString());
		assertEquals(cupo, resultado);
	}

	@Test
	void testdeleteByFechaAndHoraAndCentroVacunacion() {
		CupoDao cupodao = mock(CupoDao.class);
		CentroVacunacion centro = new CentroVacunacion("Prueba", "MunicipioPrueba", 1000);
		((CupoDao) doNothing().when(cupodao)).deleteByFechaAndHoraAndCentroVacunacion(anyString(), anyString(),any());
		LocalTime time = LocalTime.now();
		LocalDate date = LocalDate.now();
		cupodao.deleteByFechaAndHoraAndCentroVacunacion(date.toString(),time.toString(), centro);
		
	}


}

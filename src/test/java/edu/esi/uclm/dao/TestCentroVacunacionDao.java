package edu.esi.uclm.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import edu.esi.uclm.model.CentroVacunacion;

class TestCentroVacunacionDao {

	@Test
	void test() {
		CentroVacunacionDao centrodao = mock(CentroVacunacionDao.class);
		CentroVacunacion esperado=new CentroVacunacion("Prueba","MunicipioPrueba", 1000);
		
		when(centrodao.findByNombre(anyString())).thenReturn(esperado);
		CentroVacunacion resultado= centrodao.findByNombre("Prueba");

		assertEquals(esperado,resultado);
	}

}

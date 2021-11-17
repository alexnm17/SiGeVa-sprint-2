package edu.esi.uclm.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class CentroVacunacion {
	@Id
	private String idCentroVacunacion;
	private String nombre;
	private String municipio;
	private int dosis;

	public CentroVacunacion() {
		this.idCentroVacunacion = UUID.randomUUID().toString();
	}

	public CentroVacunacion(String nombre, String municipio, int dosis) {
		this.idCentroVacunacion = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.municipio = municipio;
		this.dosis = dosis;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public int getDosis() {
		return dosis;
	}

	public void setDosis(int dosis) {
		this.dosis = dosis;
	}

	public String getIdCentroVacunacion() {
		return idCentroVacunacion;
	}

	public void setIdCentroVacunacion(String idCentroVacunacion) {
		this.idCentroVacunacion = idCentroVacunacion;
	}

}

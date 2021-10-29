package edu.esi.uclm.model;

import org.springframework.data.annotation.Id;

public class CentroVacunacion {
	@Id
	private String nombre;
	private String municipio;

	public CentroVacunacion() {

	}

	public CentroVacunacion(String nombre, String municipio) {
		this.nombre = nombre;
		this.municipio = municipio;
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

}

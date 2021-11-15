package edu.esi.uclm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class CentroVacunacion {
	@Id @Field("nombre")
	private String nombre;
	private String municipio;
	private int dosis;

	public CentroVacunacion() {

	}

	public CentroVacunacion(String nombre, String municipio, int dosis) {
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

}

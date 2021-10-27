package edu.esi.uclm.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class CentroVacunacion {
	private String nombre;
	private String municipio;
	@DBRef
	private ListaVacunacion listaVacunacion;
	
	public CentroVacunacion() {
		
	}
	
	public CentroVacunacion(String nombre, String municipio, ListaVacunacion listaVacunacion) {
		this.nombre = nombre;
		this.municipio = municipio;
		this.listaVacunacion=listaVacunacion;
	}
	
	public ListaVacunacion getListaVacunacion() {
		return listaVacunacion;
	}

	public void setListaVacunacion(ListaVacunacion listaVacunacion) {
		this.listaVacunacion = listaVacunacion;
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

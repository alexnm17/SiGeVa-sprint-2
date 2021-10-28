package edu.esi.uclm.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class ListaVacunacion {

	private String fecha;
	@DBRef
	private	List<Cita> listaCitas;
	
	private String centroSalud;
	
	public ListaVacunacion() {
		
	}

	public ListaVacunacion(String fecha, List<Cita> listaCitas, String centroSalud) {
		this.fecha = fecha;
		this.listaCitas = listaCitas;
		this.centroSalud = centroSalud;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<Cita> getListaCitas() {
		return listaCitas;
	}

	public void setListaCitas(List<Cita> listaCitas) {
		this.listaCitas = listaCitas;
	}

	public String getCentroSalud() {
		return centroSalud;
	}

	public void setCentroSalud(String centroSalud) {
		this.centroSalud = centroSalud;
	}


	
	
}

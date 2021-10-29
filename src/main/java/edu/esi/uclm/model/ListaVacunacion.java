package edu.esi.uclm.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class ListaVacunacion {

	private Date fecha;
	@DBRef
	private	Cita cita;
	
	
	public ListaVacunacion() {
		
	}

	public ListaVacunacion(Date fecha, Cita cita) {
		this.fecha = fecha;
		this.cita = cita;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}
	
	
}

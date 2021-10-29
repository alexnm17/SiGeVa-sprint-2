package edu.esi.uclm.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class ListaVacunacion {

	private Date fecha;
	@DBRef
	private	List<Cita> cita;
	
	
	public ListaVacunacion() {
		
	}

	public ListaVacunacion(Date fecha, List<Cita> cita) {
		this.fecha = fecha;
		this.cita = cita;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Cita> getCita() {
		return cita;
	}

	public void setCita(List<Cita> cita) {
		this.cita = cita;
	}
	
	
}

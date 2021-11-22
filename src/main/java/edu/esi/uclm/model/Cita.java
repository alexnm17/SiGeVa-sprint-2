package edu.esi.uclm.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Cita {
	@Id
	private String idCita;
	private String fecha;
	private String hora;
	@DBRef
	private Usuario usuario;
	@DBRef
	private CentroVacunacion centroVacunacion;
	private boolean isUsada;

	public Cita() {
		this.idCita = UUID.randomUUID().toString();
	}

	public Cita(String fecha, String hora, Usuario usuario) {
		this.fecha = fecha;
		this.hora = hora;
		this.centroVacunacion = usuario.getCentroVacunacion();
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public CentroVacunacion getCentroVacunacion() {
		return centroVacunacion;
	}

	public void setCentroVacunacion(CentroVacunacion centroVacunacion) {
		this.centroVacunacion = centroVacunacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdCita() {
		return idCita;
	}

	public void setIdCita(String idCita) {
		this.idCita = idCita;
	}

	public boolean getIsUsada() { 
		return isUsada;
	}

	public void setIsUsada(boolean isUsada) {
		this.isUsada = isUsada;
	}
	
	
}

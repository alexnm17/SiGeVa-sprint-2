package edu.esi.uclm.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

import edu.uclm.esi.exceptions.SiGeVaException;

public class Cita {
	private String fecha;
	private String hora;
	@DBRef
	private Usuario usuario;
	@DBRef
	private CentroVacunacion centroVacunacion;

	public Cita() {
		// El constructor vacio ha sido crado por exigencias del Spring
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
}

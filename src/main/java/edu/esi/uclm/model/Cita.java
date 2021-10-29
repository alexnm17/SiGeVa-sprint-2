package edu.esi.uclm.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Cita {
	private String fecha;
	private String hora;
	@DBRef
	private List<Usuario> listaUsuario;
	@DBRef
	private CentroVacunacion centroVacunacion;

	public Cita() {
		// El constructor vacio ha sido crado por exigencias del Spring
	}

	public Cita(String fecha, String hora, CentroVacunacion centro) {
		this.fecha = fecha;
		this.hora = hora;
		this.listaUsuario = new ArrayList<>();
		this.centroVacunacion = centro;
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

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public CentroVacunacion getCentroVacunacion() {
		return centroVacunacion;
	}

	public void setCentroVacunacion(CentroVacunacion centroVacunacion) {
		this.centroVacunacion = centroVacunacion;
	}

}

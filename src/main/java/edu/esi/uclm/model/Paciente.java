package edu.esi.uclm.model;

public class Paciente extends Usuario {
	//Hace falta esto?
	private String historiaClinica;
	private EstadoVacunacion estadoVacunacion;
	
	public Paciente() {	
		//El constructor vacio ha sido crado por exigencias del Spring
	}
	
	public Paciente(String dni, String nombre, String apellido, String password, String historiaClinica, String rol) {
		super(dni, nombre, apellido, password, rol);
		this.historiaClinica = historiaClinica;
		this.estadoVacunacion = EstadoVacunacion.NO_VACUNADO;
	}

	public String getHistoriaClinica() {
		return historiaClinica;
	}

	public EstadoVacunacion getEstadoVacunacion() {
		return estadoVacunacion;
	}

	public void setHistoriaClinica(String historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public void setEstadoVacunacion(EstadoVacunacion estadoVacunacion) {
		this.estadoVacunacion = estadoVacunacion;
	}

}

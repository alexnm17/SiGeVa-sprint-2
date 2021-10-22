package edu.esi.uclm.model;

public class Paciente extends Usuario {
	private String historiaClinica;
	private EstadoVacunacion estadoVacunacion;
	
	public Paciente() {	}
	
	public Paciente(String dni, String nombre, String apellido, String password, String historiaClinica) {
		super(dni,nombre,apellido, password);
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

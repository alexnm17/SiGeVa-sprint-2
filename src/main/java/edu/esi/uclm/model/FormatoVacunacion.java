package edu.esi.uclm.model;

public class FormatoVacunacion {
	private String horaInicioVacunacion;
	private String horaFinVacunacion;
	private String duracionFranjaVacunacion;
	private int personasPorFranja;
	
	public FormatoVacunacion() {
		//El constructor vacio ha sido crado por exigencias del Spring
	}
	
	public FormatoVacunacion(String horaInicioVacunacion, String horaFinVacunacion, String duracionFranjaVacunacion,
			int personasPorFranja) {
		this.horaInicioVacunacion = horaInicioVacunacion;
		this.horaFinVacunacion = horaFinVacunacion;
		this.duracionFranjaVacunacion = duracionFranjaVacunacion;
		this.personasPorFranja = personasPorFranja;
	}

	public String getHoraInicioVacunacion() {
		return horaInicioVacunacion;
	}

	public void setHoraInicioVacunacion(String horaInicioVacunacion) {
		this.horaInicioVacunacion = horaInicioVacunacion;
	}

	public String getHoraFinVacunacion() {
		return horaFinVacunacion;
	}

	public void setHoraFinVacunacion(String horaFinVacunacion) {
		this.horaFinVacunacion = horaFinVacunacion;
	}

	public String getDuracionFranjaVacunacion() {
		return duracionFranjaVacunacion;
	}

	public void setDuracionFranjaVacunacion(String duracionFranjaVacunacion) {
		this.duracionFranjaVacunacion = duracionFranjaVacunacion;
	}

	public int getPersonasPorFranja() {
		return personasPorFranja;
	}

	public void setPersonasPorFranja(int personasPorFranja) {
		this.personasPorFranja = personasPorFranja;
	}
	
	
	
}

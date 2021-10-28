package edu.esi.uclm.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoVacunacion {
	private String horaInicioVacunacion;
	private String horaFinVacunacion;
	private int duracionFranjaVacunacion;
	private int personasPorFranja;
	
	public FormatoVacunacion() {
		//El constructor vacio ha sido crado por exigencias del Spring
	}
	
	public FormatoVacunacion(String horaInicioVacunacion, String horaFinVacunacion, int duracionFranja,
			int personasPorFranja) {
		this.horaInicioVacunacion = horaInicioVacunacion;
		this.horaFinVacunacion = horaFinVacunacion;
		this.duracionFranjaVacunacion = duracionFranja;
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

	public int getDuracionFranjaVacunacion() {
		return duracionFranjaVacunacion;
	}

	public void setDuracionFranjaVacunacion(int duracionFranjaVacunacion) {
		this.duracionFranjaVacunacion = duracionFranjaVacunacion;
	}

	public int getPersonasPorFranja() {
		return personasPorFranja;
	}

	public void setPersonasPorFranja(int personasPorFranja) {
		this.personasPorFranja = personasPorFranja;
	}
	
	public boolean horasCorrectas() {
		try {
			Date horaInicio = new SimpleDateFormat("HH:mm").parse(horaInicioVacunacion);
			Date horaFin = new SimpleDateFormat("HH:mm").parse(horaFinVacunacion);
			return horaInicio.before(horaFin);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return false; 
	}
	
}

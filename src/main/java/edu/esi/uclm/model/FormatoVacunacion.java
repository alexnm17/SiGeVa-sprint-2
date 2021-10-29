package edu.esi.uclm.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uclm.esi.exceptions.SiGeVaException;

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
	
	public boolean horasCorrectas() throws SiGeVaException{
		try {
			Date horaInicio = new SimpleDateFormat("HH:mm").parse(horaInicioVacunacion);
			Date horaFin = new SimpleDateFormat("HH:mm").parse(horaFinVacunacion);
			return horaInicio.before(horaFin);
		} catch (ParseException e) {
			throw new SiGeVaException (null, "El formato introducido no es válido ");
		}
	}
	
}

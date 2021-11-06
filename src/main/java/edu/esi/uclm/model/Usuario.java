package edu.esi.uclm.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;

import edu.uclm.esi.exceptions.SiGeVaException;

public class Usuario {
	@Id
	private String dni;
	private String nombre;
	private String apellido;
	private String password;
	private String rol;
	private String centroSalud;

	private String estadoVacunacion;

	public Usuario() {
	}

	public Usuario(String dni, String nombre, String apellido, String password, String rol, String centroSalud,
			String estadoPaciente) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.rol = rol;
		this.centroSalud = centroSalud;
		this.estadoVacunacion = EstadoVacunacion.NO_VACUNADO.name();

	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getPassword() {
		return password;
	}

	public String getRol() {
		return rol;
	}

	public String getCentroSalud() {
		return centroSalud;
	}

	public String getEstadoVacunacion() {
		return estadoVacunacion;
	}
	
	public void setCentroSalud(String centroSalud) {
		this.centroSalud = centroSalud;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void setEstadoVacunacion(String estadoVacunacion) {
		this.estadoVacunacion = estadoVacunacion;
	}

	public void controlarContraseña() throws SiGeVaException {
		if (password.length() != 8)
			throw new SiGeVaException(HttpStatus.CONFLICT, "La contraseña no tiene la longitud adecuada");
		if (password.equals(password.toLowerCase()))
			throw new SiGeVaException(HttpStatus.CONFLICT, "La contraseña no contiene una letra mayuscula");
		if (password.equals(password.toUpperCase()))
			throw new SiGeVaException(HttpStatus.CONFLICT, "La contraseña no contiene una letra minuscula");
		if (!(password.contains(".") || password.contains(",") || password.contains("-") || password.contains("_")))
			throw new SiGeVaException(HttpStatus.CONFLICT, "La contraseña no tiene ningun signo de los indicados");

	}

	public void comprobarDni() throws SiGeVaException {
		char[] cadenaDni = dni.toCharArray();
		for (int i = 0; i < 7; i++)
			if (!Character.isDigit(cadenaDni[i]))
				throw new SiGeVaException(HttpStatus.CONFLICT, "No cumple con el formato de un DNI");
		if (!Character.isLetter(cadenaDni[7]))
			throw new SiGeVaException(HttpStatus.CONFLICT, "No cumple con el formato de un DNI");
	}

	public void comprobarEstado() throws SiGeVaException {
		if (!estadoVacunacion.equals("No Vacunado"))
			throw new SiGeVaException(HttpStatus.CONFLICT,
					"No se puede completar este proceso ya que el usuario ya esta vacunado");

	}
}

package edu.esi.uclm.model;

import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.esi.uclm.exceptions.SigevaException;

public class Usuario {
	@Id
	@Field("email")
	private String email;
	private String dni;
	private String nombre;
	private String apellido;
	private String password;
	private String rol;
	@DBRef
	private CentroVacunacion centroVacunacion;

	private String estadoVacunacion;

	// Esto valdrá para validar el email
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public Usuario() {
	}

	public Usuario(String email, String dni, String nombre, String apellido, String password, String rol,
			CentroVacunacion centroVacunacion) {
		this.email = email;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.rol = rol;
		this.centroVacunacion = centroVacunacion;
		this.estadoVacunacion = EstadoVacunacion.NO_VACUNADO.name();

	}

	public String getEmail() {
		return email;
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public String getRol() {
		return rol;
	}

	public String getEstadoVacunacion() {
		return estadoVacunacion;
	}

	public void setEstadoVacunacion(String estadoVacunacion) {
		this.estadoVacunacion = estadoVacunacion;
	}

	public CentroVacunacion getCentroVacunacion() {
		return centroVacunacion;
	}

	public void setCentroVacunacion(CentroVacunacion centroVacunacion) {
		this.centroVacunacion = centroVacunacion;
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
		this.password = DigestUtils.sha512Hex(password);
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void comprobarEstado() throws SigevaException {
		if (!estadoVacunacion.equals(EstadoVacunacion.NO_VACUNADO.name()))
			throw new SigevaException(HttpStatus.NOT_MODIFIED,
					"No se puede completar este proceso ya que el usuario ya esta vacunado");
	}

	public void setPasswordModify(String password) {
		this.password = password;
	}

}

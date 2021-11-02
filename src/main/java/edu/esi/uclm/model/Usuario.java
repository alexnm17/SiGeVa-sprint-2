package edu.esi.uclm.model;

import org.springframework.data.annotation.Id;

public class Usuario {
	@Id
	private String dni;
	private String nombre;
	private String apellido;
	private String password;
	private String rol;
	private String centroSalud;

	public Usuario() {
	}

	public Usuario(String dni, String nombre, String apellido, String password, String rol, String centroSalud) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.rol = rol;
		this.centroSalud = centroSalud;
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
}

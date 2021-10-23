package edu.esi.uclm.model;

public class Usuario {
	private String dni;
	private String nombre;
	private String apellido;
	private String password;
	private String rol;
	private String centro;

	public Usuario() {
		//El constructor vacio ha sido crado por exigencias del Spring
	}
	
	public Usuario(String dni, String nombre, String apellido, String password, String rol) {
		this.dni=dni;
		this.nombre=nombre;
		this.apellido=apellido;
		this.password=password;
		this.rol = rol;
	
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

	public String getCentro() {
		return centro;
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

	public void setCentro(String centro) {
		this.centro = centro;
	}

}

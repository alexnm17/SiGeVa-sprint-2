package edu.esi.uclm.model;

public class Administrador extends Usuario{
	private String rol;
	
	
	public Administrador() {
		super();
	}
	
	public Administrador(String dni, String nombre, String apellido, String password) {
			super(dni, nombre, apellido, password);
			this.rol="administrador";
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}

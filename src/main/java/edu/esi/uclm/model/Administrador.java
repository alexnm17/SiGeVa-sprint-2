package edu.esi.uclm.model;

public class Administrador extends Usuario{
	private String rol;
	
	
	public Administrador() {
		super();
	}
	
	public Administrador(String dni, String nombre, String apellido, String password, String centroSalud) {
			super(dni, nombre, apellido, password,centroSalud);
			this.rol="administrador";
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}

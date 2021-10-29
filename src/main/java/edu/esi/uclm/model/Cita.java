package edu.esi.uclm.model;


public class Cita {

	private String id;
	private String fecha;
	
	public Cita() {
		//El constructor vacio ha sido crado por exigencias del Spring
	}

	public Cita(String id, String fecha) {
		this.id = id;
		this.fecha = fecha;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	

}

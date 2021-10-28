package edu.esi.uclm.model;


public class Cita {
	private String id;
	private String hora;
	private String assignedTo;
	
	public Cita() {
		//El constructor vacio ha sido crado por exigencias del Spring
	}
	
	public Cita(String hora) {
		this.hora = hora;
		this.assignedTo = "";
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

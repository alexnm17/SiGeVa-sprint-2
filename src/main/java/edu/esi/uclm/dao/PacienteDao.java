package edu.esi.uclm.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.Paciente;

public interface PacienteDao extends MongoRepository<Paciente, String> {
	//public Paciente findByDni(String dni);
}

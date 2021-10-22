package edu.esi.uclm.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.Cita;

public interface CitaDao extends MongoRepository<Cita, String> {
	
}

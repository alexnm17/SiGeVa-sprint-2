package edu.esi.uclm.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.esi.uclm.model.CentroVacunacion;

@Repository
public interface CentroVacunacionDao extends MongoRepository<CentroVacunacion, String> {
	CentroVacunacion findByNombre(String nombre);

}

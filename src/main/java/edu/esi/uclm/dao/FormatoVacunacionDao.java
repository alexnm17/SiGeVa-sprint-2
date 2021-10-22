package edu.esi.uclm.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.esi.uclm.model.FormatoVacunacion;

@Repository
public interface FormatoVacunacionDao extends MongoRepository<FormatoVacunacion, String> {

}

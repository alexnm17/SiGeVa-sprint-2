package edu.esi.uclm.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.esi.uclm.model.ListaVacunacion;

@Repository
public interface ListaVacunacionDao extends MongoRepository<ListaVacunacion, String> {

}

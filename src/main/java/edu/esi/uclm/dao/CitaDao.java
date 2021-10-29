package edu.esi.uclm.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;

public interface CitaDao extends MongoRepository<Cita, String> {
	List<Cita> findAllByCentroVacunacion(CentroVacunacion centroVacunacion);
}

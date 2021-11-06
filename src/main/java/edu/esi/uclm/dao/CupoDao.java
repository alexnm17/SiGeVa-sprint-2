package edu.esi.uclm.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cupo;


public interface CupoDao extends MongoRepository<Cupo, String> {

	List<Cupo> findAllByCentroVacunacion(CentroVacunacion centroVacunacion);

	Cupo findByFechaAndHora(String fecha, String hora);

	void deleteByFechaAndHoraAndCentroVacunacion(String fecha, String hora,CentroVacunacion Vacunacion);
	
}

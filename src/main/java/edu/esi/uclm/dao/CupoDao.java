package edu.esi.uclm.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cupo;

public interface CupoDao extends MongoRepository<Cupo, String> {

	Cupo findByFechaAndHora(String fecha, String hora);

	void deleteByFechaAndHoraAndCentroVacunacion(String fecha, String hora, CentroVacunacion vacunacion);

	Cupo findByFechaAndHoraAndCentroVacunacion(String fecha, String hora, CentroVacunacion centro);

	List<Cupo> findAllByCentroVacunacionAndFecha(CentroVacunacion centroVacunacion, String fecha);

	List<Cupo> findAllByCentroVacunacion(CentroVacunacion centroVacunacion);

	Cupo findAllByCentroVacunacionAndFechaAndHora(CentroVacunacion centroVacunacion, String fecha, String hora);

}

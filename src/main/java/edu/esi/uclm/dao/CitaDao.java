package edu.esi.uclm.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;

public interface CitaDao extends MongoRepository<Cita, String> {

	List<Cita> findAllByCentroVacunacion(CentroVacunacion centroVacunacion);

	List<Cita> findAllByUsuarioDni(String usuarioDni);
	
	Cita findByFechaAndHora(String fecha, String hora);

	void deleteByFechaAndHora(String fecha, String hora);

	Cita findByUsuarioDni(String dni);

	List<Cita> findAllByFechaAndCentroVacunacion(String fecha, String centroVacunacion);

	void deleteAllByUsuarioDni(String dni);

	
}

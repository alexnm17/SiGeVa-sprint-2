package edu.esi.uclm.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Usuario;

public interface CitaDao extends MongoRepository<Cita, String> {

	Cita findByFechaAndHora(String fecha, String hora);

	void deleteByFechaAndHora(String fecha, String hora);

	List<Cita> findAllByFechaAndCentroVacunacion(String fecha, CentroVacunacion centroVacunacion);

	List<Cita> findAllByUsuario(Usuario usuario);

	Cita findByUsuario(Usuario usuario);

	void deleteAllByUsuario(Usuario usuario);

	Cita findByIdCita(String string);

	void deleteByIdCita(String idCita);

	List<Cita> findAllByUsuarioEmail(String email);

	Cita findByUsuarioAndFecha(Usuario usuario, String fecha);
}

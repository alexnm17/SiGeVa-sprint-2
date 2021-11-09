package edu.esi.uclm.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.Usuario;

public interface CitaDao extends MongoRepository<Cita, String> {

	List<Cita> findAllByCentroVacunacion(CentroVacunacion centroVacunacion);

	Cita findByFechaAndHora(String fecha, String hora);

	void deleteByFechaAndHora(String fecha, String hora);

	List<Cita> findAllByFechaAndCentroVacunacion(String fecha, String centroVacunacion);

	List<Cita> findAllByUsuario(Usuario usuario);

	Cita findByUsuario(Usuario usuario);

	void deleteAllByUsuario(Usuario usuario);

	/*void deleteAllByUsuarioDni(String dni);
	 * Cita findByUsuarioDni(String dni);
	 * List<Cita> findAllByUsuarioDni(String usuarioDni);
	 */

	
}

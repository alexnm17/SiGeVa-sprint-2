package edu.esi.uclm.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.esi.uclm.model.Usuario;

@Repository
public interface UsuarioDao extends MongoRepository<Usuario, String> {

	Usuario findByDni(String dni);

	//void deleteByDni(String dni);

	//Usuario findByDniAndRol(String dni, String rol);

	Usuario findByEmail(String email);

	Usuario findByEmailAndPassword(String email, String password);

}

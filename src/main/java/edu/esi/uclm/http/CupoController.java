package edu.esi.uclm.http;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.CupoDao;
import edu.esi.uclm.dao.UsuarioDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cupo;
import edu.esi.uclm.model.Usuario;

@RestController
public class CupoController {

	@Autowired
	private CupoDao cupoDao;
	@Autowired
	private UsuarioDao usuarioDao;

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getAllCuposConHuecoPorFecha")
	public List<Cupo> getAllCuposConHuecoPorFecha(@RequestParam String email, @RequestParam String fecha) {
		Optional<Usuario> optUsuario = usuarioDao.findById(email);
		CentroVacunacion centroVacunacion = new CentroVacunacion();
		if (optUsuario.isPresent())
			centroVacunacion = optUsuario.get().getCentroVacunacion();

		List<Cupo> listaCupos = cupoDao.findAllByCentroVacunacionAndFecha(centroVacunacion, fecha);
		List<Cupo> listaCuposLibres = new ArrayList<>();

		listaCupos.sort(Comparator.comparing(Cupo::getFecha));

		Cupo cupo;
		for (int i = 0; i < listaCupos.size(); i++) {
			cupo = listaCupos.get(i);
			if (cupo.getPersonasRestantes() > 0) {
				listaCuposLibres.add(cupo);
			}
		}

		return listaCuposLibres;
	}
}

package edu.esi.uclm.http;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.PacienteDao;
import edu.esi.uclm.model.Paciente;

@RestController
public class PacienteController {

	@Autowired
	private PacienteDao pacienteDao;

	@GetMapping("/pruebaConfigGet")
	public void pruebaConfig(HttpSession session) {
		for (Paciente paciente : pacienteDao.findAll()) {
			System.out.println(paciente);
		}

	}

	@PostMapping("/pruebaConfigPost")
	public void pruebaConfigPost(HttpSession session) {
		System.out.println("Pasa por el post");
		pacienteDao
				.save(new Paciente("20617021M", "Jaime", "León", "ContraSuperSegura", "No le hja pasado nunca nada"));

	}

}

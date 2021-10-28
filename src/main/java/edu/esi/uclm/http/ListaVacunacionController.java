package edu.esi.uclm.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.esi.uclm.dao.CentroVacunacionDao;
import edu.esi.uclm.dao.CitaDao;
import edu.esi.uclm.dao.FormatoVacunacionDao;
import edu.esi.uclm.dao.ListaVacunacionDao;
import edu.esi.uclm.model.CentroVacunacion;
import edu.esi.uclm.model.Cita;
import edu.esi.uclm.model.FormatoVacunacion;
import edu.esi.uclm.model.ListaVacunacion;

@RestController
public class ListaVacunacionController {
	
	@Autowired
	private CentroVacunacionDao centroVacunacionDao;
	
	@Autowired
	private ListaVacunacionDao listaVacunacionDao ;
	
	@Autowired
	private FormatoVacunacionDao formatoVacunacionDao ;
	
	@Autowired
	private CitaDao citaDao;

	public void consultarLista() {

	}

	public void setVacunado() {

	}
	
	@GetMapping("/crearListasVacunacion")
	public void crearListaVacunacion() {
		Optional<FormatoVacunacion> optformato = formatoVacunacionDao.findById("61786ae2d452371261588e26");
		FormatoVacunacion formato = optformato.get();
		
		
		List<CentroVacunacion> centrosVacunacion = centroVacunacionDao.findAll();
		
		int horaFin = LocalTime.parse(formato.getHoraFinVacunacion()).getHour();
		int horaInicio = LocalTime.parse(formato.getHoraInicioVacunacion()).getHour();
		double duracion = (double) formato.getDuracionFranjaVacunacion()/60;
		int numFranjas = (int) ((horaFin - horaInicio)/duracion);
		
		
		for(int i = 0; i<centrosVacunacion.size();i++) {
			LocalDate fechaLista = LocalDate.now();
			
			while(fechaLista.isBefore(LocalDate.parse(LocalDate.now().plusYears(1).getYear()+"-01-01"))) {
				System.out.println("Creando lista del dia : "+fechaLista);
			
				List <Cita> listaCitas=crearCitas(formato.getPersonasPorFranja(),numFranjas,LocalTime.parse(formato.getHoraFinVacunacion()), formato.getDuracionFranjaVacunacion());
				ListaVacunacion listaVac = new ListaVacunacion(fechaLista.toString(), listaCitas, centrosVacunacion.get(i).getNombre());
				
				listaVacunacionDao.save(listaVac);
				
				fechaLista = fechaLista.plusDays(1);
			}
		}
	
		
	}
	@PostMapping("/crearCitas")
	public List<Cita> crearCitas(int personasMax, int numFranjas, LocalTime horaInicio, int duracion) {
		List <Cita> listaCitas = new ArrayList<Cita>();
		
		for(int i = 0; i<numFranjas;i++) {
			
			for(int j =0 ; j<personasMax ; j++) {
				Cita cita = new Cita(horaInicio.toString());
				listaCitas.add(cita);
				citaDao.save(cita);
			}
			horaInicio = horaInicio.plusMinutes(duracion);
		}
		return listaCitas;

	}
	

}

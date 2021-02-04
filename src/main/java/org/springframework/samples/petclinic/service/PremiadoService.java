package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Feedback;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.model.WallOfFame;
import org.springframework.samples.petclinic.repository.FeedbackRepository;
import org.springframework.samples.petclinic.repository.PagoRepository;
import org.springframework.samples.petclinic.repository.PremiadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PremiadoService {
	
	private PremiadoRepository premiadoRepository;
	private WallOfFameService wallOfFameService;
	private AlumnoService alumnoService;
	private PagoRepository pagoRepository;
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	public PremiadoService(PremiadoRepository premiadoRepository, WallOfFameService wallOfFameService
			, AlumnoService alumnoService, PagoRepository pagoRepository, FeedbackRepository feedbackRepository) {
		this.premiadoRepository = premiadoRepository;
		this.wallOfFameService = wallOfFameService;
		this.alumnoService = alumnoService;
		this.pagoRepository = pagoRepository;
		this.feedbackRepository = feedbackRepository;
	}

	public List<Premiado> premiadosPorFecha(String fechaWall){
		return premiadoRepository.premiadosPorFecha(fechaWall);
	}
	
	public String obtenerUltimaSemana() {
		return premiadoRepository.lastWallOfFame();
	}
	
	@Transactional
	public void insertarPremiado(Alumno alumno, String fechaWall, String descripcion, MultipartFile file) throws DataAccessException, IOException {
		 
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/photosWall");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		  	 
	    byte[] bytes = file.getBytes(); 
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + alumno.getNickUsuario() + ".jpg");
	    Files.write(rutaCompleta, bytes); 
		  
		Premiado p = new Premiado();
		p.setAlumnos(alumno);
		p.setDescripcion(descripcion);
		p.setFoto(alumno.getNickUsuario() + ".jpg");
		 
		Optional<WallOfFame> wallofFame = wallOfFameService.getWallById(fechaWall);
		if(wallofFame.isPresent()) {	//existe el wall, no hay que crearlo
			p.setWalloffames(wallofFame.get());
			premiadoRepository.save(p);
			
		}else { //no existe
			WallOfFame w = new WallOfFame();
			w.setFechaWall(fechaWall);
			WallOfFame newWall = wallOfFameService.saveWallOfFame(w);
			p.setWalloffames(newWall);
			premiadoRepository.save(p);
			
		}
		 
	}
	 
	@Transactional
	public void editarPremiado(Integer id, String descripcion, MultipartFile file, String nickUsuario) throws DataAccessException, IOException {
		if(file !=null) { 
			Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/photosWall");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
					
			byte[] bytes = file.getBytes(); 
			Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nickUsuario + ".jpg");
			Files.write(rutaCompleta, bytes);
		}
		
		if(!descripcion.isEmpty()) {
			log.info("Changing description: "+descripcion);
			Optional<Premiado> p = premiadoRepository.findById(id);
			if(p.isPresent()) {
				Premiado premiado = p.get();
				premiado.setDescripcion(descripcion);
				premiadoRepository.save(premiado); 
			} 
		}
	}

	@Transactional
	public void deletePremiadoById(Integer id) throws DataAccessException {
		Optional<Premiado> p = premiadoRepository.findById(id);
		if(p.isPresent()) {
			WallOfFame wall = p.get().getWalloffames();
			premiadoRepository.deleteById(id);
			List<Premiado> premiados = premiadoRepository.premiadosPorFecha(wall.getFechaWall());
			if(premiados.size() < 1) {
				wallOfFameService.deleteWallOfFame(wall);
			}
		}
		
	}
	
	public Integer numAparicionesEnFecha(String fechaWall, String nickUsuario) {
		return premiadoRepository.numAparicionesEnFecha(fechaWall, nickUsuario);
	}
	
	public void deleteAlumno(String idAlumno) {
		
		Alumno a = alumnoService.getAlumno(idAlumno);
		
		List<Pago> listPago = pagoRepository.findPaymentsByStudent(idAlumno);
		for(Pago p: listPago) {
			pagoRepository.delete(p);
		}
		
		List<Premiado> listPremiado = premiadoRepository.aparacionesPorAlumno(idAlumno);
		for(Premiado p: listPremiado) {
			premiadoRepository.delete(p);
		}
		
		List<Feedback> listFeedback = feedbackRepository.findFeedbacksByStudent(idAlumno);
		for(Feedback f: listFeedback) {
			feedbackRepository.delete(f);
		}	
		
		alumnoService.deleteStudents(a);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

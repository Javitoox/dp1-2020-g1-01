package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.model.WallOfFame;
import org.springframework.samples.petclinic.repository.PremiadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PremiadoService {
	
	@Autowired
	private PremiadoRepository premiadoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private WallOfFameService wallOfFameService;

	public List<Premiado> premiadosPorFecha(String fechaWall){
		return premiadoRepository.premiadosPorFecha(fechaWall);
	}
	
	public String obtenerUltimaSemana() {
		return premiadoRepository.lastWallOfFame();
	}
	
	public void insertarPremiado(String nickUsuario, String fechaWall, String descripcion, MultipartFile file) {
		
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/photosWall");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		  
		try { 
		  byte[] bytes = file.getBytes(); 
		  Path rutaCompleta =
	  
		  Paths.get(rutaAbsoluta + "//" + nickUsuario + ".jpg");
		  Files.write(rutaCompleta, bytes); 
		  
		} catch (IOException e) { 
			  e.printStackTrace(); 
		}
				  
		
		Alumno alumno = alumnoService.getAlumno(nickUsuario);
		Premiado p = new Premiado();
		p.setAlumnos(alumno);
		p.setDescripcion(descripcion);
		p.setFoto(nickUsuario + ".jpg");
		
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
	
	public void editarPremiado(Integer id, String descripcion) {
		Optional<Premiado> p = premiadoRepository.findById(id);
		if(p.isPresent()) {
			Premiado premiado = p.get();
			premiado.setDescripcion(descripcion);
			premiadoRepository.save(premiado);
		}
		
	}

	public void deletePremiadoById(Integer id) {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

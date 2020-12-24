package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.model.WallOfFame;
import org.springframework.samples.petclinic.repository.PremiadoRepository;
import org.springframework.stereotype.Service;


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
	
	public void insertarPremiado(String nickUsuario, String fechaWall, String descripcion) {
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
	
}

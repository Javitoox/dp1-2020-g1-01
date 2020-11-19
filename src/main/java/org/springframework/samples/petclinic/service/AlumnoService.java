package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	//No haría falta crear instancia no? El Autowired ya te la crea
	public AlumnoService(AlumnoRepository alumnoRepository) {
		this.alumnoRepository=alumnoRepository;
	}
	
	public Alumno getAlumno(String nickUsuario) {
		return alumnoRepository.findByNick(nickUsuario);
	}
	

	public List<Alumno> getAlumnos() {
		return alumnoRepository.findAll();
	}
}

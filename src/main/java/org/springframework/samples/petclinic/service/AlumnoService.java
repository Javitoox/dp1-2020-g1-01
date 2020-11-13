package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {

	@Autowired
	AlumnoRepository alumnoRepo;
	
	public Collection<Alumno>getAlumnos(){
		return alumnoRepo.findAll();
	}
	
	public Optional<Alumno>findById(int id){
		return alumnoRepo.findById(id);
	}
	
	public void saveAlumno(Alumno a){
		alumnoRepo.save(a);
	}

	public void deleteAlumno(Alumno a) {
		alumnoRepo.deleteById(a.getId());
	}
	
}

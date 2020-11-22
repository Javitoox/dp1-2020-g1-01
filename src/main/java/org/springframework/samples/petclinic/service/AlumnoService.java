package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoService {
	
	@Autowired
	AlumnoRepository alumnoRepository;
	
	public Alumno getAlumno(String nickUsuario) {
		return alumnoRepository.findByNick(nickUsuario);
	}
	public List<Alumno> getAllAlumnos() {
		return alumnoRepository.findAll();
	} 
	@Transactional
	public void saveAlumno(Alumno alumno) throws DataAccessException {
		//creating alumno
		alumnoRepository.save(alumno);		
	}		

    public List<Alumno> getStudentsByCourse(String cursoDeIngles){
        System.out.println("**"+cursoDeIngles+"**");
        return alumnoRepository.findStudentsByCourse(cursoDeIngles);
    }
}


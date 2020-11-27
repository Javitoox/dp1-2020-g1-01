package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {
	
	@Autowired
	AlumnoRepository alumnoRepository;
	
	public Alumno getAlumno(String nickUsuario) {
		return alumnoRepository.findByNick(nickUsuario);
	}
	
	
	public List<Alumno> getStudentsPerGroup(String nombreGrupo) {
        return alumnoRepository.findByGroup(nombreGrupo);
    }
	
	public List<Alumno> getAllAlumnos() {
		return alumnoRepository.findStudents();
	} 
	@Transactional
	public void saveAlumno(Alumno alumno) throws DataAccessException {
		alumnoRepository.save(alumno);		
	}		

    public List<Alumno> getStudentsByCourse(String cursoDeIngles){
        return alumnoRepository.findStudentsByCourse(cursoDeIngles);
    }
   
}


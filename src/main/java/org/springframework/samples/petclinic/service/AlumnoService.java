package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.web.AlumnoController;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlumnoService {
	
	AlumnoRepository alumnoRepository;
	GrupoService grupoService;
	
	@Autowired
	public AlumnoService(AlumnoRepository alumnoRepository) {
		this.alumnoRepository = alumnoRepository;
	}
	
	public List<Alumno> getStudentsPerGroup(String nombreGrupo) {
        return alumnoRepository.findByGroup(nombreGrupo);
    }
	
	public List<Alumno> getAllAlumnos() {
		return alumnoRepository.findStudents();
	}
	
	public Alumno getAlumno(String nickUsuario) {
		return alumnoRepository.findByNick(nickUsuario);
	}
		
	public void deleteStudents(Alumno alumno) {
		alumnoRepository.delete(alumno);
	}
	
	@Transactional
	public Alumno saveAlumno(Alumno alumno) throws DataAccessException {
		return alumnoRepository.save(alumno);		
	}		

    public List<Alumno> getStudentsByCourse(TipoCurso cursoDeIngles){
        return alumnoRepository.findStudentsByCourse(cursoDeIngles);
    }
   
    public List<Alumno>getAllMyStudents(String nickTutor){
    	return alumnoRepository.findStudentsByTutor(nickTutor);
    }
    
    /*Parte de grupos*/
    
    public Grupo getGrupo(String id) {
    	return grupoService.getGroupById(id);
    }
    
    
    
}


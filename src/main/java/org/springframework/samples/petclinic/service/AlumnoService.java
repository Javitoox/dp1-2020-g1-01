package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.Inscripcion;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {
	
	private AlumnoRepository alumnoRepository;
	private InscripcionService inscripcionService;
	
	@Autowired
	public AlumnoService(AlumnoRepository alumnoRepository, InscripcionService inscripcionService) {
		this.alumnoRepository = alumnoRepository;
		this.inscripcionService = inscripcionService;
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
	
	@Transactional	
	public void deleteStudents(Alumno alumno) throws DataAccessException{
		alumnoRepository.delete(alumno);
	}
	@Transactional	
	public void deleteStudent(String id) throws DataAccessException {
		alumnoRepository.deleteById(id);
	}
	@Transactional
	public Alumno saveAlumno(Alumno alumno) throws DataAccessException {
		return alumnoRepository.save(alumno);		
	}		
	
    public List<Alumno> getStudentsByCourse(TipoCurso cursoDeIngles){
        return alumnoRepository.findStudentsByCourse(cursoDeIngles);
    }
    
    public List<String> getStudentsWithNoGroups(){
        return alumnoRepository.findSudentsWithNoGroups();
    }
   
    public List<Alumno>getAllMyStudents(String nickTutor){
    	return alumnoRepository.findStudentsByTutor(nickTutor);
    }
    
    public List<String> getStudentsToDelete(){
    	return alumnoRepository.findStudentsAbleToDelete();
    }
     
    public Boolean existStudent(String id){
    	Alumno a = alumnoRepository.findById(id).orElse(null);
    	if(a!=null) {
    		return true;
    	}else {
    		return false;
    	}
    }
     
    public void asignInscripcionesAlumnos(Evento evento, TipoCurso tipoCurso, String type) {
    	List<Alumno> alumnosCurso = getStudentsByCourse(tipoCurso);
		Integer idInscripcion = inscripcionService.lastId()+1;
		for(Alumno a: alumnosCurso) {
			Inscripcion i = new Inscripcion();
			i.setId(idInscripcion);
			i.setFecha(LocalDate.now());
			if(type.equals("internal"))
				i.setRegistrado(true);
			else
				i.setRegistrado(false);
			i.setEvento(evento);
			i.setAlumno(a);
			inscripcionService.saveInscripcion(i);
			idInscripcion++;
		}
    }
    
}


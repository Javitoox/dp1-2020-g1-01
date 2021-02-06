package org.springframework.samples.petclinic.service;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
	
	private AlumnoService alumnoService;
	private ProfesorService profesorService;
	private TutorService tutorService;
	
	@Autowired
	public UsuarioService(AlumnoService alumnoService, ProfesorService profesorService, TutorService tutorService) {
		this.alumnoService = alumnoService;
		this.profesorService = profesorService;
		this.tutorService = tutorService;
	}
	
	public Pair<String, String> getUser(String nickUsuario) {
		Pair<String, String> type = null;
		Alumno a = alumnoService.getAlumno(nickUsuario);
		if(a!=null && a.getFechaMatriculacion()!=null) { // Si se cumple esta condición, quiere decir que el username existe entre los alumnos registrados
			type = new Pair<String, String>("alumno", a.getContraseya());
		}
		Profesor p = profesorService.getProfesor(nickUsuario);
		if(p!=null) { // Si se cumple esta condición, quiere decir que el username existe entre los profesores registrados
			type = new Pair<String, String>("profesor", p.getContraseya());
		}
		Tutor t = tutorService.getTutor(nickUsuario);
		if(t!=null && t.getFechaMatriculacion()!=null) { // Si se cumple esta condición, quiere decir que el username existe entre los tutores registrados
			type = new Pair<String, String>("tutor", t.getContraseya());
		}
		log.info("User -> "+type);
		return type;
	}
	
}

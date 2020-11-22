package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private ProfesorService profesorService;
	
	@Autowired
	private TutorService tutorService;
	
	public String typeOfUser(String nickUsuario, String contraseya) {
		String type = "Username not exist";
		Alumno a = alumnoService.getAlumno(nickUsuario);
		Profesor p = profesorService.getProfesor(nickUsuario);
		Tutor t = tutorService.getTutor(nickUsuario);
		if(a!=null) type = a.getContraseya().equals(contraseya) ? "integrante":"Incorrect password";
		if(p!=null) type = p.getContraseya().equals(contraseya) ? "integrante":"Incorrect password";
		if(t!=null) type = t.getContraseyaTutor().equals(contraseya) ? "tutor":"Incorrect password";
		return type;
	}
	
}

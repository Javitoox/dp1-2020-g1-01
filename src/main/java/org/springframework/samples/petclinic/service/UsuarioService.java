package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private ProfesorService profesorService;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository=usuarioRepository;
	}
	
	public String typeOfUser(String nickUsuario) {
		String type = "Username not exist";
		Alumno a = alumnoService.getAlumno(nickUsuario);
		Profesor p = profesorService.getProfesor(nickUsuario);
		Tutor t = tutorService.getTutor(nickUsuario);
		if(a!=null) type = "integrante";
		if(p!=null) type = "integrante";
		if(t!=null) type = "tutor";
		return type;
	}
	
	public Boolean existPassword(String nickUsuario, String contraseya) {
		String p = usuarioRepository.findByNick(nickUsuario).getContraseya();
		return p.equals(contraseya);
	}
	
	
}

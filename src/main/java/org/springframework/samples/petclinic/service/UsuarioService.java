package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	/*@Autowired
	private ProfesorService profesorService;
	
	@Autowired
	private TutorService tutorService;
	*/
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository=usuarioRepository;
	}
	
	public String typeOfUser(String nickUsuario) {
		Alumno a = alumnoService.getAlumno(nickUsuario);
		return null;
	}
	
	
}

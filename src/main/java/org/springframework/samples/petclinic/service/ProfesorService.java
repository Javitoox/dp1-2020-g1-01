package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfesorService {
	
	private ProfesorRepository profesorRepository;
	
	@Autowired
	public ProfesorService(ProfesorRepository profesorRepository) {
		this.profesorRepository=profesorRepository;
	}
	
	public Profesor getProfesor(String nickUsuario) {
		return profesorRepository.findById(nickUsuario).orElse(null);
	}

}

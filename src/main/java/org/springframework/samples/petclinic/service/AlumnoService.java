package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {
	
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	public AlumnoService(AlumnoRepository alumnoRepository) {
		this.alumnoRepository=alumnoRepository;
	}
	
	public Alumno getAlumno(String nickUsuario) {
		return alumnoRepository.findByNick(nickUsuario);
	}
	
	
	public Collection<Alumno> getStudentsPerGroup(String nombreGrupo) {
        return alumnoRepository.findByGroup(nombreGrupo);
    }

}

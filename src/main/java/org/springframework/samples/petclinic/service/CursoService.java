package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.repository.CursoRepository;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;
    
    public Optional<Curso> getCourseById(String nombreCurso) {
    	return cursoRepository.findById(nombreCurso);
    }
}

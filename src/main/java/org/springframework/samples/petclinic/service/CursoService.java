package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.CursoRepository;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    private CursoRepository cursoRepository;
    
    @Autowired
    public CursoService(CursoRepository cursoRepository) {
    	this.cursoRepository = cursoRepository;
    }
    
    public Curso getCourseById(TipoCurso nombreCurso) {
    	return cursoRepository.findById(nombreCurso).orElse(null);
    }
    
}

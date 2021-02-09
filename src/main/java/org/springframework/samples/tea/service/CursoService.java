package org.springframework.samples.tea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.repository.CursoRepository;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    private CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
    	this.cursoRepository = cursoRepository;
    }

    public Curso getCourseById(String nombreCurso) {
    	return cursoRepository.findById(nombreCurso).orElse(null);
    }

}

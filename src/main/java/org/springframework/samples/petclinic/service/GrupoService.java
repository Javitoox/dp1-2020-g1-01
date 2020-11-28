package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {
	
	private GrupoRepository grupoRepository;

	@Autowired
	public GrupoService(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}
	
	public Collection<Grupo> findAll(){
		return grupoRepository.findAll();
	}
	public List<Grupo> getGrupo(String curso) {
		return grupoRepository.findByCurso(curso);
	}
	
	public Grupo getCourseById(String nombreCurso) {
    	return grupoRepository.findById(nombreCurso).get();
	}
	
	

}

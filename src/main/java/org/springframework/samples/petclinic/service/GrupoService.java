package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
	
	public Optional<Grupo> getGrupo(String id) {
		return grupoRepository.findById(id);
	}
	
	public List<Grupo> getGrupos(String curso) {
		return grupoRepository.findByCurso(curso);
	}
	
	@Transactional
	public Grupo saveGroup(Grupo grupo) {
		return grupoRepository.save(grupo);		
	}
	
	public void deleteGroup(Grupo grupo) {
		grupoRepository.delete(grupo);
	}

	
	
	public Grupo getCourseById(String nombreCurso) {
    	return grupoRepository.findById(nombreCurso).get();
	}
	
	

}

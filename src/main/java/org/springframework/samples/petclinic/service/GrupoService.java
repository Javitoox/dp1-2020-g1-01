package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	
	public Set<Grupo> getAllGrupos(){
		return grupoRepository.findAll();
	}
	
	public Optional<Grupo> getGrupo(String id) {
		return grupoRepository.findById(id);
	}
	
	public List<Grupo> getGrupos(String curso) {
		return grupoRepository.findByCurso(curso);
	}
	
	@Transactional
	public void saveGroup(Grupo grupo) {
		grupoRepository.save(grupo);		
	}

	@Transactional
	public void deleteGroup(String nombreGrupo) {
		grupoRepository.deleteById(nombreGrupo);
	}	
	
	public Grupo getGroupById(String nombreGrupo) {
    	return grupoRepository.findById(nombreGrupo).get();
	}
	
	public Grupo crearGrupo(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
}

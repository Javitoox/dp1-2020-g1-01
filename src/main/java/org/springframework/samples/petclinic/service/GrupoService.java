package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {
	
	private GrupoRepository grupoRepository;	

	@Autowired
	public GrupoService(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}
	
	public boolean exists(String id) {
		return grupoRepository.existsById(id);
	}
	
	@Transactional(readOnly = true)
	public Set<Grupo> getAllGrupos(){
		return grupoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<String> getNameGruposByCourse(TipoCurso curso) {
		return grupoRepository.findNameByCurso(curso);
	}
	
	@Transactional(readOnly = true)
	public List<String> getCursoByGrupo(String grupo) {
		return grupoRepository.findNameByGrupo(grupo);
	}
		
	@Transactional(readOnly = true)
	public List<String> getGroupNames() {
		return grupoRepository.findAllGroupNames();
	}
	
	@Transactional(readOnly = true)
	public List<String> getEmptyGroups() {
		return grupoRepository.findAllEmptyGroups();
	}
	
	@Transactional
	public void saveGroup(Grupo grupo){
		grupoRepository.save(grupo);
	}

	@Transactional
	public void deleteGroup(String id){
		grupoRepository.deleteById(id);
	}
	
}

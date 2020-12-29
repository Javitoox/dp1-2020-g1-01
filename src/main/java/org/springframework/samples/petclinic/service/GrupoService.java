package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.samples.petclinic.service.exceptions.BadRequestException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {
	
	private GrupoRepository grupoRepository;	

	@Autowired
	public GrupoService(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}
	
	@Transactional(readOnly = true)
	public Set<Grupo> getAllGrupos(){
		return grupoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Grupo> getGrupo(String id) {
		return grupoRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Grupo> getGruposByCourse(String curso) {
		return grupoRepository.findByCurso(curso);
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
	public void saveGroup(Grupo grupo) throws DuplicatedGroupNameException{
		if(grupoRepository.existsById(grupo.getNombreGrupo())) {
			throw new DuplicatedGroupNameException();
		}else {
			grupoRepository.save(grupo);
		}	
	}

	@Transactional
	public void deleteGroup(String id) throws BadRequestException{
		try {
			grupoRepository.deleteById(id);

		}catch (BadRequestException e) {
			throw new BadRequestException("No se puede borrar el grupo porque tiene alumnos");
		}
	}
	
	@Transactional(readOnly = true)
	public Grupo getGroupById(String id) {
    	return grupoRepository.findById(id).get();
	}
	
	
}

package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.samples.petclinic.service.exceptions.BadRequestException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.samples.petclinic.web.GrupoController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrupoService {
	
	private GrupoRepository grupoRepository;	
	private AlumnoService alumnoService;

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
	
	@Transactional
	public void saveGroup(Grupo grupo) throws DuplicatedGroupNameException{
//		if(grupoRepository.findById(grupo.getNombreGrupo()).isPresent()) {
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
////		List<Alumno> als = alumnoService.getStudentsPerGroup(id);
////		if(!als.isEmpty()) {
////			throw new BadRequestException("No se puede borrar el grupo porque tiene alumnos");
////		}else {
//		if(alumnoService.getStudentsPerGroup(id).size()==0) {
//			grupoRepository.deleteById(id);
//		}else {
//			throw new BadRequestException("No se puede borrar el grupo porque tiene alumnos");
//		}
////		}
//		
//	}	
	
	@Transactional(readOnly = true)
	public Grupo getGroupById(String id) {
    	return grupoRepository.findById(id).get();
	}
	
//	@Transactional
//	public Grupo crearGrupo(Grupo grupo) throws DuplicatedGroupNameException, BadRequestException{
//		if(this.grupoRepository.findById(grupo.getNombreGrupo()).get() != null) {
//			throw new DuplicatedGroupNameException();
//		}else {
//			return grupoRepository.save(grupo);
//		}
//	}
	
}

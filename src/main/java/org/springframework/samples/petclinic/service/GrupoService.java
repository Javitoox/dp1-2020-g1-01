package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class GrupoService {
	
	private AlumnoService alumnoService;
	private GrupoRepository grupoRepository;

	@Autowired
	public GrupoService(GrupoRepository grupoRepository, AlumnoService alumnoService) {
		this.grupoRepository = grupoRepository;
		this.alumnoService = alumnoService;
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
		this.grupoRepository.save(grupo);
	}
		
	@Transactional
	public void deleteGroup(String id){
			this.grupoRepository.deleteById(id);
	}
	
	public Boolean grupoVacio(String nombreGrupo){
		return alumnoService.getStudentsPerGroup(nombreGrupo).isEmpty();
	}
	
	public Integer numAlumnos(String nombreGrupo) {
		Integer num = grupoRepository.numAlumnosGrupo(nombreGrupo).size();
		log.info("NUMERO DE ALUMNOSSSS: {}", num);
		return num;
	}
}

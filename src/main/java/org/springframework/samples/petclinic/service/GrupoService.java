package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.repository.CursoRepository;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class GrupoService {
	
	private GrupoRepository grupoRepository;
	private AlumnoRepository alumnoRepository;
	private CursoRepository cursoRepository;

	@Autowired
	public GrupoService(GrupoRepository grupoRepository, AlumnoRepository alumnoRepository, CursoRepository cursoRepository) {
		this.grupoRepository = grupoRepository;
		this.alumnoRepository = alumnoRepository;
		this.cursoRepository = cursoRepository;
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
	
	//change
	public Grupo crearGrupo(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	/*
	 * Pongo aquí los métodos para poder poner en grupoController solo grupoService
	 * */
	
	public Alumno findAlumnById(String id) {
    	return alumnoRepository.findById(id).get();
	}
	
	@Transactional
	public void saveAlumn(Alumno alumn) {
		alumnoRepository.save(alumn);		
	}
	
}

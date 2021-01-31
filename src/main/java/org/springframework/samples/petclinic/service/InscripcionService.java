package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Inscripcion;
import org.springframework.samples.petclinic.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

@Service
public class InscripcionService {
	
	private InscripcionRepository inscripcionRepository;

	@Autowired
	public InscripcionService(InscripcionRepository inscripcionRepository) {
		this.inscripcionRepository = inscripcionRepository;
	}
	
	public Integer lastId() {
		List<Inscripcion> inscripciones = inscripcionRepository.findAll();
		return inscripciones.size()>0 ? inscripciones.get(inscripciones.size()-1).getId() : 0;
	}
	
	public List<Inscripcion> inscripcionesEvento(Integer idEvento){
		return inscripcionRepository.findInscripcionesEvento(idEvento);
	}
	
	@Transactional
	public void saveInscripcion(Inscripcion i) throws DataAccessException{
		inscripcionRepository.save(i);
	}

}

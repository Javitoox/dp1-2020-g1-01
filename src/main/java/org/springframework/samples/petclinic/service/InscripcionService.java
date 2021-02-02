package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.Inscripcion;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.repository.EventoRepository;
import org.springframework.samples.petclinic.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

@Service
public class InscripcionService {
	
	private InscripcionRepository inscripcionRepository;
	private AlumnoRepository alumnoRepository;
	private EventoRepository eventoRepository;

	@Autowired
	public InscripcionService(InscripcionRepository inscripcionRepository, AlumnoRepository alumnoRepository, EventoRepository eventoRepository) {
		this.inscripcionRepository = inscripcionRepository;
		this.alumnoRepository = alumnoRepository;
		this.eventoRepository = eventoRepository;
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
	
	public Inscripcion getInscripcionByEventoAlumno(Evento e, Alumno a) {
		Inscripcion result = null;
		List<Inscripcion> inscripciones = (List<Inscripcion>) a.getInscripciones();
		for(Inscripcion i: inscripciones) {
			if(i.getEvento().getId().equals(e.getId())) {
				result = i;
				break;
			}
		}
		return result;
	}
	
	@Transactional
	public Boolean joinOrDisjoin(Integer id, String nick, Boolean join) throws DataAccessException{
		Boolean result = false;
		Alumno a = alumnoRepository.findByNick(nick);
		Evento evento = eventoRepository.findById(id).orElse(null);
		if(a != null && evento != null && evento.getTipo().getTipo().equals("external")) {
			Inscripcion i = getInscripcionByEventoAlumno(evento, a);
			i.setRegistrado(join);
			inscripcionRepository.save(i);
			result = true;
		}
		return result;
	}

}

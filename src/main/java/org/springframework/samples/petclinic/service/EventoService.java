package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.repository.EventoRepository;
import org.springframework.samples.petclinic.util.DateFormatter;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
	
	private EventoRepository eventoRepository;
	
	@Autowired
	public EventoService(EventoRepository eventoRepository) {
		this.eventoRepository = eventoRepository;
	}
	
	public List<Evento> getAll(){
		return eventoRepository.findAllEvents();
	}
	
	@Transactional
	public Evento updateDateEvent(Integer id, String s, String e) throws DataAccessException{
		Evento evento = eventoRepository.findById(id).orElse(null);
		if(evento != null && s != null) {
			LocalDate start = DateFormatter.StringToLocalDate(s);
			LocalDate end = DateFormatter.StringToLocalDate(e);
			evento.setStart(start);
			evento.setEnd(end);
			eventoRepository.save(evento);
		}
		return evento;
	}

}

package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.repository.EventoRepository;
import org.springframework.samples.petclinic.util.Colors;
import org.springframework.samples.petclinic.util.DateFormatter;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
	
	private EventoRepository eventoRepository;
	private TipoEventoService tipoEventoService;
	
	@Autowired
	public EventoService(EventoRepository eventoRepository, TipoEventoService tipoEventoService) {
		this.eventoRepository = eventoRepository;
		this.tipoEventoService = tipoEventoService;
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
	
	public String getDescription(Integer id) {
		Evento evento = eventoRepository.findById(id).orElse(null);
		String result = null;
		if(evento != null) {
			result = evento.getDescripcion()+"/"+evento.getTipo().getTipo();
		}
		return result;
	}
	
	@Transactional
	public void deleteDescription(Integer id) throws DataAccessException{
		eventoRepository.deleteById(id);
	}
	
	public Evento getEvento(Integer id) {
		return eventoRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void saveEvent(Evento evento) throws DataAccessException{
		eventoRepository.save(evento);
	}
	
	@Transactional
	public Boolean assignTypeAndSave(Evento evento, String type) throws DataAccessException{
		TipoEvento t = tipoEventoService.getType(type);
		if(t != null) {
			evento.setTipo(t);
			evento.setColor(Colors.generateRandomColor());
			eventoRepository.save(evento);
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean existEvent(Evento evento) {
		Evento result = eventoRepository.findExist(evento.getTitle(), evento.getStart());
		return result != null ? true:false;
	}

}

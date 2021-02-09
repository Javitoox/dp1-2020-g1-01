package org.springframework.samples.tea.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.model.Evento;
import org.springframework.samples.tea.model.Inscripcion;
import org.springframework.samples.tea.model.TipoEvento;
import org.springframework.samples.tea.repository.EventoRepository;
import org.springframework.samples.tea.util.Colors;
import org.springframework.samples.tea.util.DateFormatter;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

	private EventoRepository eventoRepository;
	private TipoEventoService tipoEventoService;
	private CursoService cursoService;
	private AlumnoService alumnoService;
	private InscripcionService inscripcionService;

	@Autowired
	public EventoService(EventoRepository eventoRepository, TipoEventoService tipoEventoService, CursoService cursoService, AlumnoService alumnoService,
			InscripcionService inscripcionService) {
		this.eventoRepository = eventoRepository;
		this.tipoEventoService = tipoEventoService;
		this.cursoService = cursoService;
		this.alumnoService = alumnoService;
		this.inscripcionService = inscripcionService;
	}

	public List<Evento> getAll(){
		return eventoRepository.findAllEvents();
	}

	public List<Evento> getAlumEvents(String nick){
		Alumno a = alumnoService.getAlumno(nick);
		List<Evento> eventos = new ArrayList<>();
		if(a !=null) {
			List<Inscripcion> inscripciones = (List<Inscripcion>) a.getInscripciones();
			for(Inscripcion i: inscripciones) {
				eventos.add(i.getEvento());
			}
		}
		return eventos;
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
			List<Inscripcion> inscripciones = inscripcionService.inscripcionesEvento(id);
			result = evento.getDescripcion()+"/"+evento.getTipo().getTipo()+"/"+
			(inscripciones.size()>0 ? inscripciones.get(0).getAlumno().getGrupos().getCursos().getCursoDeIngles() : "");
			for(Inscripcion i: inscripciones) {
				if(i.getRegistrado()) result += "/"+i.getAlumno().getNombreCompletoUsuario();
			}
		}
		return result;
	}

	public String getDescriptionAlumno(Integer id, String nickUser) {
		Alumno a = alumnoService.getAlumno(nickUser);
		Evento evento = eventoRepository.findById(id).orElse(null);
		String result = null;
		if(evento != null && a != null) {
			Inscripcion i = inscripcionService.getInscripcionByEventoAlumno(evento, a);
			result = evento.getDescripcion()+"/"+evento.getTipo().getTipo()+"/"+i.getRegistrado();
		}
		return result;
	}

	@Transactional
	public void deleteDescription(Integer id) throws DataAccessException{
		eventoRepository.deleteById(id);
	}

	@Transactional
	public Boolean assignEvent(Evento evento, String type, String curso) throws DataAccessException{
		TipoEvento t = tipoEventoService.getType(type);
		Curso c = cursoService.getCourseById(curso);
		if(t != null && c != null) {
			evento.setTipo(t);
			evento.setColor(Colors.generateRandomColor());
			eventoRepository.save(evento);
			Integer idEvento = eventByPersonalId(evento.getTitle(), evento.getStart()).getId();
			evento.setId(idEvento);
			alumnoService.asignInscripcionesAlumnos(evento, c.getCursoDeIngles(), type);
			return true;
		}else {
			return false;
		}
	}

	public Boolean existEvent(Evento evento) {
		Evento result = eventoRepository.findExist(evento.getTitle(), evento.getStart());
		return result != null ? true:false;
	}

	public Evento eventByPersonalId(String title, LocalDate start) {
		return eventoRepository.findExist(title, start);
	}

}

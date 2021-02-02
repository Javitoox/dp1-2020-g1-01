package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Inscripcion;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.repository.EventoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EventoServiceTests {
	
	private static Evento e;
	
	private static Alumno a;
	
	private static Inscripcion i;
	
	@Mock
	private EventoRepository eventoRepository;
	
	@Mock
	private TipoEventoService tipoEventoService;
	
	@Mock
	private CursoService cursoService;
	
	@Mock
	private AlumnoService alumnoService;
	
	@Mock
	private InscripcionService inscripcionService;
	
	@InjectMocks
	protected EventoService eventoService;
	
	@BeforeAll
	void data() {
		i = new Inscripcion();
		i.setFecha(LocalDate.of(2021, 1, 7));
		i.setRegistrado(true);
		
		List<Inscripcion> inscripciones = new ArrayList<>();
		inscripciones.add(i);
		
		e = new Evento();
		TipoEvento tipo = new TipoEvento();
		e.setTipo(tipo);
		e.setTitle("Tea League");
		e.setStart(LocalDate.parse("2020-12-18"));
		e.setDescripcion("Amazing league");
		e.setInscripciones(inscripciones);
		
		a = new Alumno();
		Grupo grupo = new Grupo();
		Curso curso = new Curso();
		curso.setCursoDeIngles(TipoCurso.B1);
		grupo.setCursos(curso);
		a.setGrupos(grupo);
		a.setNickUsuario("JaviMarFer");
		a.setInscripciones(inscripciones);
		
		i.setAlumno(a);
		i.setEvento(e);
	}
	
	@Test
	void shouldFindEvents() {
        List<Evento> eventos = new ArrayList<Evento>();
        eventos.add(e);
        when(eventoRepository.findAllEvents()).thenReturn(eventos);
        
        List<Evento> es = eventoService.getAll();
        
        assertThat(es).hasSize(1);
        Evento e = es.iterator().next();
        assertThat(e.getTitle()).isEqualTo("Tea League");
        assertThat(e.getStart()).isEqualTo(LocalDate.parse("2020-12-18"));
	}
	
	@Test
	void shouldFindEventsbyStudent() {
        List<Evento> eventos = new ArrayList<Evento>();
        eventos.add(e);
        when(alumnoService.getAlumno(any())).thenReturn(a); 
        
        List<Evento> es = eventoService.getAlumEvents("JaviMarFer");
        
        assertThat(es).hasSize(1);
        Evento e = es.iterator().next();
        assertThat(e.getTitle()).isEqualTo("Tea League");
        assertThat(e.getStart()).isEqualTo(LocalDate.parse("2020-12-18"));
	}
	
	@Test
	void shouldFindEventsbyStudentNotExist() {
        when(alumnoService.getAlumno(any())).thenReturn(null); 
        
        List<Evento> es = eventoService.getAlumEvents("JaviMarFer");
        
        assertThat(es).hasSize(0);
	}
	
	@Test
	void shouldUpdateEvent() {
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(any())).thenReturn(op);
		
		Evento evento = eventoService.updateDateEvent(1, 
				"Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)", null);
		
		assertThat(evento).isNotNull();
		assertThat(evento.getTitle()).isEqualTo("Tea League");
		verify(eventoRepository, times(1)).save(any());
	}
	
	@Test
	void shouldntUpdateEventByEventNull() {
		Optional<Evento> op = Optional.empty();
		when(eventoRepository.findById(any())).thenReturn(op);
		
		Evento evento = eventoService.updateDateEvent(1, 
				"Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)", null);
		
		assertThat(evento).isNull();
		verify(eventoRepository, times(0)).save(any());
	}
	
	@Test
	void shouldntUpdateEventByStartNull() {
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(any())).thenReturn(op);
		
		Evento evento = eventoService.updateDateEvent(1, null, null);
		
		assertThat(evento).isNotNull();
		assertThat(evento.getTitle()).isEqualTo("Tea League");
		verify(eventoRepository, times(0)).save(any());
	}
	
	@Test
	void shouldGetDescription() {
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(any())).thenReturn(op);
		when(inscripcionService.inscripcionesEvento(any())).thenReturn((List<Inscripcion>) e.getInscripciones());
		
		String description = eventoService.getDescription(1);
		String[] partes = description.split("/");
		
		assertThat(description).isNotEmpty();
		assertThat(description).isNotNull();
		assertThat(partes.length).isGreaterThan(2);
		assertThat(partes[partes.length-1]).isEqualTo("JaviMarFer");
	}
	
	@Test
	void shouldGetDescriptionNull() {
		Optional<Evento> op = Optional.empty();
		when(eventoRepository.findById(any())).thenReturn(op);
		
		String description = eventoService.getDescription(1);

		assertThat(description).isNull();
	}
	
	@Test
	void shouldGetDescriptionInscriptionsEmpty() {
		List<Inscripcion> inscripciones = new ArrayList<>();
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(any())).thenReturn(op);
		when(inscripcionService.inscripcionesEvento(any())).thenReturn(inscripciones);
		
		String description = eventoService.getDescription(1);
		String[] partes = description.split("/");
		
		assertThat(description).isNotEmpty();
		assertThat(description).isNotNull();
		assertThat(partes.length).isEqualTo(2);
	}
	
	@Test
	void shouldGetDescriptionStudentNotRegistered() {
		List<Inscripcion> inscripciones = new ArrayList<>();
		Inscripcion inscripcion = new Inscripcion();
		inscripcion.setRegistrado(false);
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(any())).thenReturn(op);
		when(inscripcionService.inscripcionesEvento(any())).thenReturn(inscripciones);
		
		String description = eventoService.getDescription(1);
		String[] partes = description.split("/");
		
		assertThat(description).isNotEmpty();
		assertThat(description).isNotNull();
		assertThat(partes[partes.length-1]).isNotEqualTo("JaviMarFer");
	}
	
	@Test
	void shouldGetStudentDescription() {
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(1)).thenReturn(op);
		when(alumnoService.getAlumno("JaviMarFer")).thenReturn(a);
		when(inscripcionService.getInscripcionByEventoAlumno(e, a)).thenReturn(i);
		
		String description = eventoService.getDescriptionAlumno(1, a.getNickUsuario());
		String[] partes = description.split("/");
		
		assertThat(description).isNotEmpty();
		assertThat(description).isNotNull();
		assertThat(partes[partes.length-1]).isEqualTo("true");
	}
	
	@Test
	void shouldGetStudentDescriptionStudentNull() {
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(1)).thenReturn(op);
		when(alumnoService.getAlumno("JaviMarFerrrrr")).thenReturn(null);
		
		String description = eventoService.getDescriptionAlumno(1, "JaviMarFerrrrr");
		
		assertThat(description).isNull();
	}
	
	@Test
	void shouldDeleteEvent() {
		eventoService.deleteDescription(1);
		
		verify(eventoRepository, times(1)).deleteById(any());
	}
	
	@Test
	void shouldAssignEvent() {
		TipoEvento tipo = new TipoEvento();
		when(tipoEventoService.getType(any())).thenReturn(tipo);
		when(cursoService.getCourseById(any())).thenReturn(a.getGrupos().getCursos());
		when(eventoRepository.findExist(any(), any())).thenReturn(e);
		
		Boolean result = eventoService.assignEvent(e, "external", "B1");
		
		assertThat(result).isTrue();
		verify(eventoRepository, times(1)).save(any());
		verify(alumnoService, times(1)).asignInscripcionesAlumnos(e, a.getGrupos().getCursos().getCursoDeIngles(), "external");
	}
	
	@Test
	void shouldAssignEventTypeNull() {
		when(tipoEventoService.getType(any())).thenReturn(null);
		when(cursoService.getCourseById(any())).thenReturn(a.getGrupos().getCursos());
		
		Boolean result = eventoService.assignEvent(e, "external", "B1");
		
		assertThat(result).isFalse();
		verify(eventoRepository, times(0)).save(any());
		verify(alumnoService, times(0)).asignInscripcionesAlumnos(e, a.getGrupos().getCursos().getCursoDeIngles(), "external");
	}

	
	@Test
	void shouldExistEvent() {
		when(eventoRepository.findExist(e.getTitle(), e.getStart())).thenReturn(e);
		
		Boolean result = eventoService.existEvent(e);
		
		assertThat(result).isTrue();
	}
	
	@Test
	void shouldNotExistEvent() {
		when(eventoRepository.findExist(e.getTitle(), e.getStart())).thenReturn(null);
		
		Boolean result = eventoService.existEvent(e);
		
		assertThat(result).isFalse();
	}

}
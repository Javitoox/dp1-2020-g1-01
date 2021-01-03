package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.repository.EventoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EventoServiceTests {
	
	private static Evento e;
	
	@Mock
	private EventoRepository eventoRepository;
	@Mock
	private TipoEventoService tipoEventoService;
	
	@InjectMocks
	protected EventoService eventoService;
	
	@BeforeAll
	void data() {
		e = new Evento();
		e.setTitle("Tea League");
		e.setStart(LocalDate.parse("2020-12-18"));
		e.setDescripcion("Amazing league");
		TipoEvento tipo = new TipoEvento();
		tipo.setTipo("internal");
		e.setTipo(tipo);
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
	@Transactional
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
		
		String description = eventoService.getDescription(1);
		
		assertThat(description).isNotEmpty();
		assertThat(description).isNotNull();
	}
	
	@Test
	void shouldGetDescriptionNull() {
		Optional<Evento> op = Optional.empty();
		when(eventoRepository.findById(any())).thenReturn(op);
		
		String description = eventoService.getDescription(1);

		assertThat(description).isNull();
	}
	
	@Test
	@Transactional
	void shouldDeleteEvent() {
		eventoService.deleteDescription(1);
		
		verify(eventoRepository, times(1)).deleteById(any());
	}
	
	@Test
	void shouldGetEvent() {
		Optional<Evento> op = Optional.of(e);
		when(eventoRepository.findById(any())).thenReturn(op);
		
		Evento evento = eventoService.getEvento(1);

		assertThat(evento).isNotNull();
		assertThat(evento.getTitle()).isEqualTo("Tea League");
	}
	
	@Test
	void shouldGetEventNull() {
		Optional<Evento> op = Optional.empty();
		when(eventoRepository.findById(any())).thenReturn(op);
		
		Evento evento = eventoService.getEvento(1);

		assertThat(evento).isNull();
	}
	
	@Test
	@Transactional
	void shouldSaveEvent() {
		eventoService.saveEvent(e);
		
		verify(eventoRepository, times(1)).save(any());
	}
	
	@Test
	@Transactional
	void shouldAssignTypeAndSave() {
		TipoEvento tipo = new TipoEvento();
		tipo.setTipo("external");
		when(tipoEventoService.getType(any())).thenReturn(tipo);
		
		Boolean result = eventoService.assignTypeAndSave(e, "external");
		
		assertThat(result).isTrue();
		assertThat(e.getTipo()).isEqualTo(tipo);
		verify(eventoRepository, times(1)).save(any());
	}
	
	@Test
	void shouldNotAssignTypeAndSave() {
		when(tipoEventoService.getType("typenotexist")).thenReturn(null);
		
		Boolean result = eventoService.assignTypeAndSave(e, "typenotexist");
		
		assertThat(result).isFalse();
		verify(eventoRepository, times(0)).save(any());
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

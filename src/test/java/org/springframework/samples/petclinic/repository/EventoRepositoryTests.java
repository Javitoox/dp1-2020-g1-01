package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Evento;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class EventoRepositoryTests {
	
	private static Evento e;
	
	@Autowired
	protected EventoRepository eventoRepository;
	
	@Autowired
	protected TipoEventoRepository tipoEventoRepository;
	
	@BeforeAll
	void data() {
		e = new Evento();
		e.setTitle("Tea League");
		e.setStart(LocalDate.of(2021, 2, 1));
		e.setEnd(LocalDate.of(2021, 2, 3));
		e.setDescripcion("Amazing league");
		e.setTipo(tipoEventoRepository.findById("internal").orElse(null));
	}
	
	@Test
	void testFindAllEvents() {
		eventoRepository.save(e);
		
		List<Evento> eventos = eventoRepository.findAllEvents();
		
		assertThat(eventos.size()).isGreaterThan(0);
	}
	
	@Test
	void testFindExistEvent() {
		eventoRepository.save(e);
		
		Evento evento = eventoRepository.findExist(e.getTitle(), e.getStart());
		
		assertThat(evento.getTitle()).isEqualTo(e.getTitle());
		assertThat(evento.getStart()).isEqualTo(e.getStart());
	}
	
	@Test
	void testFindExistEventNull() {
		eventoRepository.save(e);
		
		Evento evento = eventoRepository.findExist(e.getTitle()+"prueba", e.getStart());
		
		assertThat(evento).isNotEqualTo(e);
		assertThat(evento).isNull();
	}

}

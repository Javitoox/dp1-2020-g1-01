package org.springframework.samples.tea.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.model.Evento;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.model.Inscripcion;
import org.springframework.samples.tea.repository.AlumnoRepository;
import org.springframework.samples.tea.repository.EventoRepository;
import org.springframework.samples.tea.repository.InscripcionRepository;
import org.springframework.samples.tea.repository.TipoEventoRepository;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InscripcionServiceTests {

	private static Evento e;

	private static Alumno a;

	private static Inscripcion i;

	@Autowired
	private TipoEventoRepository tipoEventoRepository;

	@Mock
	private InscripcionRepository inscripcionRepository;

	@Mock
	private AlumnoRepository alumnoRepository;

	@Mock
	private EventoRepository eventoRepository;

	@InjectMocks
	protected InscripcionService inscripcionService;

	@BeforeAll
	void data() {
		i = new Inscripcion();
		i.setFecha(LocalDate.of(2021, 1, 7));
		i.setRegistrado(true);
		i.setId(1);

		List<Inscripcion> inscripciones = new ArrayList<>();
		inscripciones.add(i);

		e = new Evento();
		e.setTitle("Tea League");
		e.setStart(LocalDate.parse("2020-12-18"));
		e.setDescripcion("Amazing league");
		e.setInscripciones(inscripciones);
		e.setId(1);

		a = new Alumno();
		Grupo grupo = new Grupo();
		Curso curso = new Curso();
		grupo.setCursos(curso);
		a.setGrupos(grupo);
		a.setNickUsuario("JaviMarFer");
		a.setInscripciones(inscripciones);

		i.setAlumno(a);
		i.setEvento(e);
	}

	@Test
	void shouldGetLastId() {
		when(inscripcionRepository.findAll()).thenReturn((List<Inscripcion>) a.getInscripciones());

		Integer id = inscripcionService.lastId();

		assertThat(id).isEqualTo(1);
	}

	@Test
	void shouldGetLastIdEmptyList() {
		when(inscripcionRepository.findAll()).thenReturn(new ArrayList<>());

		Integer id = inscripcionService.lastId();

		assertThat(id).isEqualTo(0);
	}

	@Test
	void shouldJoin() {
		e.setTipo(tipoEventoRepository.findById("External").orElse(null));
		Optional<Evento> op = Optional.of(e);
		when(alumnoRepository.findById(a.getNickUsuario())).thenReturn(Optional.of(a));
		when(eventoRepository.findById(1)).thenReturn(op);

		Boolean result = inscripcionService.joinOrDisjoin(1, a.getNickUsuario(), true);

		assertThat(result).isTrue();
		verify(inscripcionRepository, times(1)).save(any());
	}

	@Test
	void shouldNotJoin() {
		e.setTipo(tipoEventoRepository.findById("Internal").orElse(null));
		Optional<Evento> op = Optional.of(e);
		when(alumnoRepository.findById(a.getNickUsuario())).thenReturn(Optional.of(a));
		when(eventoRepository.findById(1)).thenReturn(op);

		Boolean result = inscripcionService.joinOrDisjoin(1, a.getNickUsuario(), true);

		assertThat(result).isFalse();
		verify(inscripcionRepository, times(0)).save(any());
	}

}

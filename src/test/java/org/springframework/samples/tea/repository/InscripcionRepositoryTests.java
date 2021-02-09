package org.springframework.samples.tea.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Evento;
import org.springframework.samples.tea.model.Inscripcion;

@DataJpaTest
public class InscripcionRepositoryTests {

	private static Evento e;

	@Autowired
	protected InscripcionRepository inscripcionRepository;

	@Autowired
	protected EventoRepository eventoRepository;

	@Autowired
	protected TipoEventoRepository tipoEventoRepository;

	@Autowired
	protected AlumnoRepository alumnoRepository;

	@BeforeEach
	@Transactional
	void data() throws DataAccessException{
		Inscripcion i = new Inscripcion();
		i.setFecha(LocalDate.of(2021, 1, 7));
		i.setRegistrado(true);

		List<Inscripcion> inscripciones = new ArrayList<>();
		inscripciones.add(i);

		e = new Evento();
		e.setTipo(tipoEventoRepository.findById("Internal").orElse(null));
		e.setTitle("Tea League");
		e.setStart(LocalDate.parse("2021-03-18"));
		e.setDescripcion("Amazing league");
		eventoRepository.save(e);

		Alumno a = new Alumno();
		a.setNickUsuario("Javitoox77");
		a.setFechaMatriculacion(LocalDate.parse("2020-12-18"));
		a.setDniUsuario("99876566W");
		a.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		a.setNombreCompletoUsuario("Javi Martínez Fernández");
		a.setDireccionUsuario("Triana de Sevilla");
		a.setCorreoElectronicoUsuario("javiii7@gmail.com");
		a.setContraseya("Pollito009");
		a.setNumTelefonoUsuario("909090909");
		alumnoRepository.save(a);

		i.setAlumno(a);
		i.setEvento(e);
		inscripcionRepository.save(i);
		e.setInscripciones(inscripciones);
		a.setInscripciones(inscripciones);
		eventoRepository.save(e);
		alumnoRepository.save(a);
		inscripcionRepository.save(i);
	}

	@Test
	void testFindInscripcionesEvento() {
		List<Inscripcion> inscripciones = inscripcionRepository.findInscripcionesEvento(e.getId());

		assertThat(inscripciones.size()).isEqualTo(1);
	}

	@Test
	void testFindInscripcionesEventoNull() {
		List<Inscripcion> inscripciones = inscripcionRepository.findInscripcionesEvento(-2);

		assertThat(inscripciones.size()).isEqualTo(0);
	}

}

package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.Inscripcion;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
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
	
	@BeforeAll
	void data() {
		Inscripcion i = new Inscripcion();
		i.setFecha(LocalDate.of(2021, 1, 7));
		i.setRegistrado(true);
		
		List<Inscripcion> inscripciones = new ArrayList<>();
		inscripciones.add(i);
		
		e = new Evento();
		e.setTipo(tipoEventoRepository.findById("internal").orElse(null));
		e.setTitle("Tea League");
		e.setStart(LocalDate.parse("2020-12-18"));
		e.setDescripcion("Amazing league");
		eventoRepository.save(e);
		
		Alumno a = new Alumno();
		a.setNickUsuario("marrambla");
		a.setFechaMatriculacion(LocalDate.parse("2020-12-18"));
		a.setDniUsuario("99876566W");
		a.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		a.setNombreCompletoUsuario("Maria Dolores Garcia");
		a.setDireccionUsuario("Triana de Sevilla");
		a.setCorreoElectronicoUsuario("mariadeldolor@gmail.com");
		a.setContraseya("Pollito009");
		a.setNumTelefonoUsuario("698898989");
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

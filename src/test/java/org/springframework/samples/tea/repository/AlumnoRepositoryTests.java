package org.springframework.samples.tea.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.model.Pago;
import org.springframework.samples.tea.model.Tutor;

@DataJpaTest
public class AlumnoRepositoryTests {

	private static Alumno a;

	@Autowired
	protected AlumnoRepository alumnoRepository;

	@Autowired
	protected TipoPagoRepository tipoPagoRepository;

	@Autowired
	protected CursoRepository cursoRepository;

	@Autowired
	protected GrupoRepository grupoRepository;

	@Autowired
	protected TutorRepository tutorRepository;

	@Autowired
	protected PagoRepository pagoRepository;


	@BeforeEach
	void data() {
		a = new Alumno();
		a.setNickUsuario("javialonso");
		a.setFechaMatriculacion(LocalDate.of(2019, 03, 13));
		a.setDniUsuario("99876566W");
		a.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		a.setNombreCompletoUsuario("Maria Dolores Garcia");
		a.setDireccionUsuario("Triana de Sevilla");
		a.setCorreoElectronicoUsuario("mariadeldolor@gmail.com");
		a.setContraseya("Pollito009");
		a.setNumTelefonoUsuario("698898989");
	}

	@Test
	@Transactional
	void testFindByNickAndNif() throws DataAccessException{
		alumnoRepository.save(a);

		Alumno  alumno = alumnoRepository.findByNickAndNif("javialonso", "77777777U");

        assertThat(alumno.getNickUsuario()).isEqualTo("javialonso");
	}

	@Test
	@Transactional
	void testFindByNickAndNifDuplicated() throws DataAccessException{
		a.setVersion(0);
		alumnoRepository.save(a);
		a.setNickUsuario("JaviMartinez");
		a.setDniUsuario("76567674T");
		a.setVersion(0);
		alumnoRepository.save(a);

		Exception exception = assertThrows(IncorrectResultSizeDataAccessException.class, () -> {alumnoRepository.findByNickAndNif("JaviMartinez", "99876566W");});

        assertThat(exception.getMessage())
        .isEqualTo("query did not return a unique result: 2; nested exception is javax.persistence.NonUniqueResultException: query did not return a unique result: 2");
	}

	@Test
	void testReturnStudentsByGroup() {
		Curso c = cursoRepository.findById("A1").get();
		Grupo g  = new Grupo();
		a.setVersion(0);
		g.setNombreGrupo("Grupo de evelyn");
		g.setCursos(c);
		grupoRepository.save(g);
		a.setGrupos(g);
		alumnoRepository.save(a);
		List<Alumno>alumnos = alumnoRepository.findByGroup("Grupo de evelyn");
        assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void testReturnStudentsByGroupIsEmpty() {
		alumnoRepository.save(a);
		List<Alumno>alumnos = alumnoRepository.findByGroup("Grupo de evelyn");
        assertThat(alumnos).isEmpty();
	}

	@Test
	void testReturnListWithStudents() {
		alumnoRepository.save(a);
		List<Alumno>alumnos = alumnoRepository.findStudents();
        assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void testReturnStudentsByCourse() {
		Curso c = cursoRepository.findById("A1").get();
		Grupo g  = new Grupo();
		g.setNombreGrupo("mi grupo fav");
		g.setCursos(c);
		Grupo grupo = grupoRepository.save(g);

		a.setGrupos(grupo);
		alumnoRepository.save(a);

		List<Alumno>alumnos = alumnoRepository.findStudentsByCourse("A1");
        assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void testReturnStudentsByTutor() {
		Tutor t = new Tutor();
		t.setNickUsuario("javierV");
		t.setFechaMatriculacion(LocalDate.of(2019, 03, 13));
		t.setDniUsuario("99876566T");
		t.setFechaNacimiento(LocalDate.of(2000, 06, 23));
		t.setNombreCompletoUsuario("Javier Garcia");
		t.setDireccionUsuario("Triana de Sevilla");
		t.setCorreoElectronicoUsuario("jvierdeldolor@gmail.com");
		t.setContraseya("Pollito0010");
		t.setNumTelefonoUsuario("668898989");

		Tutor tutor = tutorRepository.save(t);
		a.setTutores(tutor);
		alumnoRepository.save(a);

		List<Alumno>alumnos = alumnoRepository.findStudentsByTutor(tutor.getNickUsuario());
        assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void testReturnStudentsNamesAbleToDelete() {
		alumnoRepository.save(a);
		Pago p = new Pago();
		p.setConcepto("Excursion");
		p.setFecha(LocalDate.of(2020, 11, 11));
		p.setTipo(tipoPagoRepository.findById("Bizum").orElse(null));
		p.setAlumnos(a);
		Pago p2 = new Pago();
		p2.setConcepto("Enrolment");
		p2.setFecha(LocalDate.of(2020, 11, 11));
		p2.setTipo(tipoPagoRepository.findById("Cash").orElse(null));
		p2.setAlumnos(a);
		Pago p3 = new Pago();
		p3.setConcepto("First Installment");
		p3.setFecha(LocalDate.of(2020, 11, 11));
		p3.setTipo(tipoPagoRepository.findById("Transfer").orElse(null));
		p3.setAlumnos(a);
		alumnoRepository.save(a);
		pagoRepository.save(p);
		pagoRepository.save(p2);
		pagoRepository.save(p3);


		List<String> allNames = alumnoRepository.findStudentsAbleToDelete();
		assertThat(allNames.size()).isEqualTo(1);
		assertThat(allNames.size()).isGreaterThan(0);
	}

	@Test
	void testReturnAlumnsAbleToDeleteIsEmpty() {
		alumnoRepository.save(a);
		Pago p = new Pago();
		p.setConcepto("Excursion");
		p.setFecha(LocalDate.of(2020, 11, 11));
		p.setTipo(tipoPagoRepository.findById("Bizum").orElse(null));
		p.setAlumnos(a);
		alumnoRepository.save(a);
		List<String> allNames = alumnoRepository.findStudentsAbleToDelete();
		assertThat(allNames.size()).isEqualTo(0);
	}

	@Test
	void testReturnStudentsNamesWithNoGroups() {
		Alumno a1 = new Alumno();
		a1.setNickUsuario("maroto");
		a1.setFechaMatriculacion(LocalDate.of(2020, 02, 10));
		a1.setDniUsuario("99876566W");
		a1.setFechaNacimiento(LocalDate.of(1999, 06, 22));
		a1.setNombreCompletoUsuario("Manuel Maroto");
		a1.setDireccionUsuario("Pino Montano");
		a1.setCorreoElectronicoUsuario("mmaroto@gmail.com");
		a1.setContraseya("TuMarotoP98");
		a1.setNumTelefonoUsuario("679581645");
		alumnoRepository.save(a1);
		List<String> allStudents = alumnoRepository.findSudentsWithNoGroups();
		assertThat(allStudents.size()).isEqualTo(1);
		assertThat(allStudents.size()).isGreaterThan(0);
	}

	@Test
	void testReturnStudentsWithNoGroupsMustBeEmpty() {
		List<String> allStudents = alumnoRepository.findSudentsWithNoGroups();
		assertThat(allStudents.size()).isEqualTo(0);
	}

}

package org.springframework.samples.petclinic.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.model.Tutor;

@DataJpaTest
public class AlumnoRepositoryTests {

	private static Alumno a;

	@Autowired 
	protected AlumnoRepository alumnoRepository;

	@Autowired
	protected CursoRepository cursoRepository;

	@Autowired
	protected GrupoRepository grupoRepository;

	@Autowired
	protected TutorRepository tutorRepository;


	@BeforeEach
	void data() { 
		a = new Alumno();
		a.setNickUsuario("marrambla");
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
	void testReturnListWithStudents() {
		alumnoRepository.save(a);
		List<Alumno>alumnos = alumnoRepository.findStudents();
        assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void testReturnStudentsByCourse() {
		Curso c = new Curso();
		c.setCursoDeIngles(TipoCurso.A1);
		Curso curso = cursoRepository.save(c);

		Grupo g  = new Grupo();
		g.setNombreGrupo("mi grupo fav");
		g.setCursos(curso);
		Grupo grupo = grupoRepository.save(g);

		a.setGrupos(grupo);
		alumnoRepository.save(a);

		List<Alumno>alumnos = alumnoRepository.findStudentsByCourse(TipoCurso.A1);
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

}







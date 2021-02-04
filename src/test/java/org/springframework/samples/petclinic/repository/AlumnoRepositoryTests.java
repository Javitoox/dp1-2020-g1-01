package org.springframework.samples.petclinic.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.model.Tutor;

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
	void testReturnStudentsByGroup() {
		Curso c = new Curso();
		c.setCursoDeIngles(TipoCurso.A1);
		Curso curso = cursoRepository.save(c);
		Grupo g  = new Grupo();
		g.setNombreGrupo("Grupo de evelyn");
		g.setCursos(curso);
		grupoRepository.save(g);
		a.setGrupos(g);
		alumnoRepository.save(a);
		List<Alumno>alumnos = alumnoRepository.findByGroup("Grupo de evelyn");
        assertThat(alumnos.size()).isGreaterThan(0);
		
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
	
	@Test
	void testReturnStudentsNamesAbleToDelete() {
		alumnoRepository.save(a);		
		Pago p = new Pago();
		p.setId(1);
		p.setConcepto("Pago matricula");
		p.setFecha(LocalDate.of(2020, 11, 11));
		p.setTipo(tipoPagoRepository.findById("bizum").orElse(null));
		p.setAlumnos(a);
		Pago p2 = new Pago();
		p2.setId(2);
		p2.setConcepto("Primer plazo");
		p2.setFecha(LocalDate.of(2020, 11, 11));
		p2.setTipo(tipoPagoRepository.findById("bizum").orElse(null));
		p2.setAlumnos(a);
		
		pagoRepository.save(p);
		pagoRepository.save(p2);

		List<String> allNames = alumnoRepository.findStudentsAbleToDelete();
		assertThat(allNames.size()).isGreaterThan(0);
	}
	
	@Test
	void testReturnStudentsNamesWithNoGroups() {
		List<String> allStudents = alumnoRepository.findSudentsWithNoGroups();
		assertThat(allStudents.size()).isGreaterThan(0);
	}

}







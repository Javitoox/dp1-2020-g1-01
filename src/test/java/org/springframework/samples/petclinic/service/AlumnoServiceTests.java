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
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.repository.GrupoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AlumnoServiceTests {

	private static List<Alumno> alumnosNotEmpty;
	private static List<Alumno> alumnosEmpty;

	private static final String CURSO_NOT_EMPTY = "B1";
	private static final String CURSO_EMPTY = "C2";

	private static final String TUTOR_WITH_STUDENTS= "PedroGar";
	private static final String TUTOR_WITHOUT_STUDENTS= "Manuel12";

	private static Grupo emptyGroup;
	private static Grupo notEmptyGroup;

	@Mock
	private AlumnoRepository alumnoRepository;
	@Mock
	private GrupoRepository grupoRepository;
	
	@Mock
	private InscripcionService inscripcionService;

	@InjectMocks
	protected AlumnoService alumnoService;

	@BeforeAll
	void data() {
		emptyGroup = new Grupo();
		emptyGroup.setNombreGrupo("Grupo A");
		notEmptyGroup = new Grupo();
		notEmptyGroup.setNombreGrupo("Grupo B");
		Curso c = new Curso();
		c.setCursoDeIngles("B1");
		notEmptyGroup.setCursos(c);
		Alumno a = new Alumno();
		a.setGrupos(notEmptyGroup);

		alumnosNotEmpty= new ArrayList<Alumno>();
		alumnosNotEmpty.add(a);

		alumnosEmpty= new ArrayList<Alumno>();

	}

	@Test
	void shouldShowStudentsListIsNotEmpty() {
		when(alumnoRepository.findStudents()).thenReturn(alumnosNotEmpty);
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void shouldShowStudentsListIsEmpty() {
		when(alumnoRepository.findStudents()).thenReturn(alumnosEmpty);
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isEqualTo(0);
	}

	@Test
	void shouldShowStudentsListByCourseIsNotNull() {
		when(alumnoRepository.findStudentsByCourse(any(String.class))).thenReturn(alumnosNotEmpty);
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(CURSO_NOT_EMPTY);
		assertThat(alumnos.size()).isGreaterThan(0);
	}

	@Test
	void shouldShowStudentsListByCourseIsNull() {
		when(alumnoRepository.findStudentsByCourse(any(String.class))).thenReturn(alumnosEmpty);
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(CURSO_EMPTY);
		assertThat(alumnos.size()).isEqualTo(0);
	}


	@Test
	void shouldShowStudentsByTutorIsNotNull() {
		when(alumnoRepository.findStudentsByTutor(any(String.class))).thenReturn(alumnosNotEmpty);
		List<Alumno> alumnos = alumnoService.getAllMyStudents(TUTOR_WITH_STUDENTS);
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	@Test
	void shouldShowStudentByNickIsNotNull() {
		Alumno a= new Alumno();
		a.setNickUsuario("Gonsalo");
		a.setContraseya("NahDeLocos99");
		a.setDniUsuario("20502441B");
		a.setCorreoElectronicoUsuario("nukescream@gmail.com");
		a.setFechaMatriculacion(LocalDate.of(2019, 10, 03));
		a.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
		a.setNumTelefonoUsuario("622110555");
		when(alumnoRepository.findById(any(String.class))).thenReturn(Optional.of(a));
		Alumno alumno = alumnoService.getAlumno(a.getNickUsuario());
		assertThat(alumno).isNotNull();
	}
	@Test
	void shouldShowStudentByNickIsNull() {
		Alumno a= new Alumno();
		a.setNickUsuario("Gonsalo");
		a.setContraseya("NahDeLocos99");
		a.setDniUsuario("20502441B");
		a.setCorreoElectronicoUsuario("nukescream@gmail.com");
		a.setFechaMatriculacion(LocalDate.of(2019, 10, 03));
		a.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
		a.setNumTelefonoUsuario("622110555");
		when(alumnoRepository.findById(any(String.class))).thenReturn(Optional.of(a));
		Alumno alumno = null;
		try {
			alumno = alumnoService.getAlumno(a.getNickUsuario());
		}catch(Exception e) {
			assertThat(alumno).isNull();
		}
	}
	@Test
	void shouldShowStudentsByTutorIsNull() {
		when(alumnoRepository.findStudentsByTutor(any(String.class))).thenReturn(alumnosEmpty);
		List<Alumno> alumnos = alumnoService.getAllMyStudents(TUTOR_WITHOUT_STUDENTS);
		assertThat(alumnos.size()).isEqualTo(0);
	}
//	@Test
//	void shouldSaveStudentAssign() {
//		Alumno a= new Alumno();
//		a.setNickUsuario("Gonsalo");
//		a.setContraseya("NahDeLocos99");
//		a.setDniUsuario("20502441B");
//		a.setCorreoElectronicoUsuario("nukescream@gmail.com");
//		a.setFechaMatriculacion(LocalDate.of(2019, 10, 03));
//		a.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
//		a.setNumTelefonoUsuario("622110555");
//		Grupo g = new Grupo();
//		when(grupoRepository.findById("Grupo2")).thenReturn(Optional.of(g));
//		
//		verify(grupoRepository, times(1)).save(any());
//	}
	@Test
	void shouldSaveStudent() {

		Alumno a= new Alumno();
		a.setNickUsuario("Gonsalo");
		a.setContraseya("NahDeLocos99");
		a.setDniUsuario("20502441B");
		a.setCorreoElectronicoUsuario("nukescream@gmail.com");
		a.setFechaMatriculacion(LocalDate.of(2019, 10, 03));
		a.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
		a.setNumTelefonoUsuario("622110555");
		Grupo g = new Grupo();
		g.setNombreGrupo("GrupoA");
		Curso curso = new Curso();
		curso.setCursoDeIngles("B2");
		g.setCursos(curso);
		a.setGrupos(g);
		alumnoService.saveAlumno(a);

		verify(alumnoRepository, times(1)).save(any());
	}
	@Test
	void shouldShowAStudentListByGroupIsNotEmpty() {
		String name = notEmptyGroup.getNombreGrupo();
		when(alumnoRepository.findByGroup(name)).thenReturn(alumnosNotEmpty);
		assertThat(alumnoService.getStudentsPerGroup(name)).isNotEmpty();
	}

	@Test
	void shouldShowAStudentListByGroupIsEmpty() {
		String name = emptyGroup.getNombreGrupo();
		when(alumnoRepository.findByGroup(name)).thenReturn(alumnosEmpty);
		assertThat(alumnoService.getStudentsPerGroup(name)).isEmpty();
	}

	@Test
	void shouldAsignInscripcionesAlumnos() {
		Evento e = new Evento();
		when(inscripcionService.lastId()).thenReturn(3);
		when(alumnoRepository.findStudentsByCourse(CURSO_NOT_EMPTY)).thenReturn(alumnosNotEmpty);

		alumnoService.asignInscripcionesAlumnos(e, CURSO_NOT_EMPTY, "internal");

		verify(inscripcionService, times(1)).saveInscripcion(any());
	 }

	@Test
	void shouldAsignInscripcionesAlumnosNotAsignInscripciones() {
		Evento e = new Evento();
		when(inscripcionService.lastId()).thenReturn(3);
		when(alumnoRepository.findStudentsByCourse(CURSO_EMPTY)).thenReturn(alumnosEmpty);

		alumnoService.asignInscripcionesAlumnos(e, CURSO_EMPTY, "internal");

		verify(inscripcionService, times(0)).saveInscripcion(any());
	 }

	@Test
	void shoulDeleteStudent() {
		Alumno a = new Alumno();
		a.setNickUsuario("Felipe");
		a.setContraseya("NahDeLocos99");
		a.setDniUsuario("20502441B");
		a.setCorreoElectronicoUsuario("felipe@gmail.com");
		a.setFechaMatriculacion(LocalDate.of(2019, 10, 03));
		a.setNombreCompletoUsuario("Felipe Gonzalez Garcia");
		a.setNumTelefonoUsuario("622110555");
		alumnoService.deleteStudents(a);
		
		verify(alumnoRepository, times(1)).save(any());
	}

	@Test
	void shouldDeleteStudentById() {
		Alumno a = new Alumno();
		a.setNickUsuario("Felipe");
		a.setContraseya("NahDeLocos99");
		a.setDniUsuario("20502441B");
		a.setCorreoElectronicoUsuario("felipe@gmail.com");
		a.setFechaMatriculacion(LocalDate.of(2019, 10, 03));
		a.setNombreCompletoUsuario("Felipe Gonzalez Garcia");
		a.setNumTelefonoUsuario("622110555");
		alumnoService.deleteStudent(a.getNickUsuario());
		verify(alumnoRepository, times(1)).deleteById("Felipe");

	}

	@Test
	void shouldReturnAllStudentsNamesWithNoGroups() {
		List<String> allNames = new ArrayList<>();
		allNames.add("Manuel Resinas"); allNames.add("Sergio Segura");
		when(alumnoRepository.findSudentsWithNoGroups()).thenReturn(allNames);
		assertThat(alumnoService.getStudentsWithNoGroups().size()).isGreaterThan(0);
		assertThat(alumnoService.getStudentsWithNoGroups().size()).isEqualTo(2);
	}

	@Test
	void shoulReturnAllStudentsNameAbleToDelete(){
		List<String> students = new ArrayList<>();
		students.add("Maribel"); students.add("Javidekuka");
		when(alumnoRepository.findStudentsAbleToDelete()).thenReturn(students);
		assertThat(alumnoService.getStudentsToDelete().size()).isEqualTo(2);
		assertThat(alumnoService.getStudentsToDelete()).isNotEmpty();
	}

}

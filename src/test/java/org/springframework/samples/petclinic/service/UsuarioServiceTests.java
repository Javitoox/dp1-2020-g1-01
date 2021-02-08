package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.Tutor;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioServiceTests {
	
	private static final String NOT_EXISTENT_USERNAME = "TESTCASE";
	
	private static final String EXISTENT_USERNAME = "TESTCASEEXIST";
	private static final String EXISTENT_PASSWORD = "TESTCase88EXIST";
	
	private static Alumno a;
	private static Profesor p;
	private static Tutor t;
	
	@Mock
	private AlumnoService alumnoService;
	@Mock
	private ProfesorService profesorService;
	@Mock
	private TutorService tutorService;
	
	protected UsuarioService usuarioService;
	
	@BeforeAll
	void data() {
		a = new Alumno();
		a.setContraseya(EXISTENT_PASSWORD);
		a.setFechaMatriculacion(LocalDate.parse("2020-12-02"));
        p = new Profesor();
		p.setContraseya(EXISTENT_PASSWORD);
		t = new Tutor();
		t.setContraseya(EXISTENT_PASSWORD);
		t.setFechaMatriculacion(LocalDate.parse("2020-12-02"));
	}
	
	@BeforeEach
	void setup() {
		usuarioService = new UsuarioService(alumnoService, profesorService, tutorService);
	}
	
	@Test
	void testTypeShouldBeAuthAlumno() {
		when(alumnoService.getAlumno(EXISTENT_USERNAME)).thenReturn(a);
		
		Pair<String, String> type = usuarioService.getUser(EXISTENT_USERNAME);
		
		assertThat(type.getValue0()).isEqualTo("alumno");
		assertThat(type.getValue1()).isEqualTo(EXISTENT_PASSWORD);
	}
	
	@Test
	void testTypeShouldBeAuthProfesor() {
		when(profesorService.getProfesor(EXISTENT_USERNAME)).thenReturn(p);
		
		Pair<String, String> type = usuarioService.getUser(EXISTENT_USERNAME);
		
		assertThat(type.getValue0()).isEqualTo("profesor");
		assertThat(type.getValue1()).isEqualTo(EXISTENT_PASSWORD);
	}
	
	@Test
	void testTypeShouldBeAuthTutor() {
		when(tutorService.getTutor(EXISTENT_USERNAME)).thenReturn(t);
		
		Pair<String, String> type = usuarioService.getUser(EXISTENT_USERNAME);
		
		assertThat(type.getValue0()).isEqualTo("tutor");
		assertThat(type.getValue1()).isEqualTo(EXISTENT_PASSWORD);
	}
	
	@Test
	void testTypeShouldBeAnyAuth() {
		Pair<String, String> type = usuarioService.getUser(NOT_EXISTENT_USERNAME);
		
		assertNull(type); // type no es compatible con assertThat
	}
	
	// Usuario service antiguo
	
//	@Test
//	void testTypeShouldBeUsernameNotExist() {
//		String type = usuarioService.typeOfUser(NOT_EXISTENT_USERNAME, NOT_EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("Username not exist");
//	}
//	
//	@Test
//	void testTypeShouldBeIncorrectPasswordAlumno() {
//		when(alumnoService.getAlumno(EXISTENT_USERNAME)).thenReturn(a);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, NOT_EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("Incorrect password");
//	}
//	
//	@Test 
//	void testTypeShouldBeIncorrectPasswordProfesor() {
//		when(profesorService.getProfesor(EXISTENT_USERNAME)).thenReturn(p);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, NOT_EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("Incorrect password");
//	}
//	
//	@Test
//	void testTypeShouldBeIncorrectPasswordTutor() {
//		when(tutorService.getTutor(EXISTENT_USERNAME)).thenReturn(t);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, NOT_EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("Incorrect password");
//	}
//	
//	@Test
//	void testTypeShouldBeAlumno() {
//		when(alumnoService.getAlumno(EXISTENT_USERNAME)).thenReturn(a);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("alumno");
//	}
//	
//	@Test
//	void testTypeShouldBeAlumnoNotRegistered() {
//		Alumno alumno = a;
//		alumno.setFechaMatriculacion(null);
//		when(alumnoService.getAlumno(EXISTENT_USERNAME)).thenReturn(alumno);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("Username not exist");
//	}
//	
//	@Test
//	void testTypeShouldBeProfesor() {
//		when(profesorService.getProfesor(EXISTENT_USERNAME)).thenReturn(p);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("profesor");
//	}
//	
//	@Test
//	void testTypeShouldBeTutor() {
//		when(tutorService.getTutor(EXISTENT_USERNAME)).thenReturn(t);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("tutor");
//	}
//	
//	@Test
//	void testTypeShouldBeTutorNotRegistered() {
//		Tutor tutor = t;
//		tutor.setFechaMatriculacion(null);
//		when(tutorService.getTutor(EXISTENT_USERNAME)).thenReturn(tutor);
//		
//		String type = usuarioService.typeOfUser(EXISTENT_USERNAME, EXISTENT_PASSWORD);
//		
//		assertThat(type).isEqualTo("Username not exist");
//	}

}

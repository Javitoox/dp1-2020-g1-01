package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class TutorServiceTests {
	
	@Autowired
	protected TutorService tutorService;
	
	@Autowired
	protected AlumnoService alumnoService;
	
	@BeforeAll
	@Transactional
	void insertTutor() {
		Tutor tutor = new Tutor();
		tutor.setContraseya("EyEyHola6");
		tutor.setCorreoElectronicoUsuario("pedro@gmail.com");
		tutor.setDireccionUsuario("Calle Lora");
		tutor.setDniUsuario("23232323H");
		tutor.setFechaNacimiento(null);
		tutor.setNickUsuario("PedroGar");
		tutor.setNombreCompletoUsuario("Pedro García");
		tutor.setNumTelefonoUsuario("676767453");
		tutorService.insert(tutor);
	}
	
	@Test
	void testListWithTutores() {
		List<Tutor> tutores = tutorService.getAllTutores();
		assertThat(tutores.size()).isGreaterThan(0);
	}
	
	@Test
	void testGetATutor() {
		Tutor tutor = tutorService.getTutor("PedroGar");
		assertThat(tutor).isNotNull();
	}
	
	@Test 
	void testStudentsByTutor() {
		Tutor tutor = tutorService.getTutor("PedroGar");
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("manu23");
		alumno.setContraseya("holaquepasa");
		alumno.setDniUsuario("44332344R");
		alumno.setDireccionUsuario("Mi casa 24");
		alumno.setNombreCompletoUsuario("Manolo Blanco");
		alumno.setNumTelefonoUsuario("776634542");
		alumno.setCorreoElectronicoUsuario("manolito@gmail.com");
		alumno.setFechaNacimiento(null);
		alumno.setTutores(tutor);
		alumnoService.saveAlumno(alumno);
		
		List<Alumno>listStudents =  alumnoService.getAllAlumnos().stream()
				.filter(x->x.getTutores().getNickUsuario().equals(tutor.getNickUsuario())).collect(Collectors.toList());
		assertFalse(listStudents.size() == 0);
	}
}

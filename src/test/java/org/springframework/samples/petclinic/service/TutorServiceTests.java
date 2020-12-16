package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
	
	@BeforeEach
	@Transactional
	void insertTutorAndStudent() {
		Tutor tutor = new Tutor();
		tutor.setContraseya("EyEyHola6");
		tutor.setCorreoElectronicoUsuario("pedro@gmail.com");
		tutor.setDireccionUsuario("Calle Lora");
		tutor.setDniUsuario("23232323H");
		tutor.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		tutor.setNickUsuario("PedroGar");
		tutor.setNombreCompletoUsuario("Pedro García");
		tutor.setNumTelefonoUsuario("676767453");
		tutorService.saveTutor(tutor);
		
		Alumno alumno = new Alumno();
		alumno.setContraseya("HolaBuenas777");
		alumno.setCorreoElectronicoUsuario("javikuka7@gmail.com");
		alumno.setDireccionUsuario("Calle error");
		alumno.setDniUsuario("76766776Y");
		alumno.setFechaMatriculacion(LocalDate.now());
		alumno.setFechaSolicitud(LocalDate.now());
		alumno.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		alumno.setNickUsuario("JaviMartinez");
		alumno.setNombreCompletoUsuario("Javi Martínez");
		alumno.setNumTelefonoUsuario("635096767");
		alumno.setNumTareasEntregadas(4);
		alumno.setTutores(tutor);
		alumnoService.saveAlumno(alumno);
	}
	
	@Test 
	void testStudentsByTutorIsNotNull() {
		Tutor tutor = tutorService.getTutorById("PedroGar").get();
		List<Alumno> alumnos = alumnoService.getAllMyStudents(tutor.getNickUsuario());
		assertFalse(alumnos.size() == 0);
	}
	
	@Test 
	void testStudentsByTutorIsNull() {
		Tutor tutor = tutorService.getTutorById("PedroGar").get();
		List<Alumno> alumnos = alumnoService.getAllMyStudents(tutor.getNickUsuario());
		for(Alumno a: alumnos) {
			alumnoService.deleteStudents(a);
		}
		List<Alumno> alumnosDeTutorBorrados = alumnoService.getAllMyStudents(tutor.getNickUsuario());
		assertTrue(alumnosDeTutorBorrados.size() == 0);
	}
}

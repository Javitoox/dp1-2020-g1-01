package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class AlumnoServiceTests {
	
	@Autowired
	protected AlumnoService alumnoService;
	protected GrupoService grupoService;

	
	
	@Transactional
	void insertAlumno() {
		Alumno alumno = new Alumno();
		alumno.setContraseya("HolaBuenas777");
		alumno.setCorreoElectronicoUsuario("javikuka7@gmail.com");
		alumno.setDireccionUsuario("Calle error");
		alumno.setDniUsuario("76766776Y");
		alumno.setFechaMatriculacion("13/8/2020");
		alumno.setFechaNacimiento("13/8/2000");
		alumno.setNickUsuario("JaviMartinez");
		alumno.setNombreCompletoUsuario("Javi Martínez");
		alumno.setNumTelefonoUsuario("635096767");
		alumno.setNumTareasEntregadas(4);
		alumnoService.saveAlumno(alumno);
	}
	
	@Test
	void testListWithAlumnos() {
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	void testStudentsByCourse() {
		String cursoDeIngles = "B1";
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(cursoDeIngles);
		assertFalse(alumnos.size() == 0);
	}
	@Test
	void getStudentsByGroup() {
		String nombreGrupo = "grupo1";
		List<Alumno> alumnos = alumnoService.getStudentsPerGroup(nombreGrupo);
		assertFalse(alumnos.size() == 0);
	}
	
	@Test
	void editStudentGroup() {
		Alumno alumno1 = alumnoService.findById("Javi");
    	Grupo grupo= grupoService.getCourseById("grupo1");       
        alumno1.setGrupos(grupo);
        alumnoService.saveAlumno(alumno1);
		assertTrue(alumno1.getGrupos().getNombreGrupo() == "grupo1");
	}
}

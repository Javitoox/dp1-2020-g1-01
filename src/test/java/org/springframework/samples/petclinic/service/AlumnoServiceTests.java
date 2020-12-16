package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class AlumnoServiceTests {
	
	@Autowired
	protected AlumnoService alumnoService;
	
	@Autowired
	protected GrupoService grupoService;

	@BeforeEach
	@Transactional
	void insertAlumno() {
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
		
		Curso curso = new Curso();
		curso.setCursoDeIngles("C2");
		
		Grupo grupo = new Grupo();
		grupo.setCursos(curso);
		grupo.setNombreGrupo("grupo1_B2");
		grupoService.saveGroup(grupo);
		
		alumno.setGrupos(grupo);
		alumnoService.saveAlumno(alumno);
	}
	
	@Test
	void testListWithAlumnos() {/*no sería mejor comprobar el tamaño de la lista de alumnos antes y despues de insertar uno nuevo?? */
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	void testStudentsListIsNotEmpty() {
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	@AfterAll
	void testStudentsListIsEmpty() {
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		for(Alumno a: alumnos) {
			alumnoService.deleteStudents(a);
		}
		List<Alumno> alumnosBorrados = alumnoService.getAllAlumnos();
		assertTrue(alumnosBorrados.size() == 0);
	}
	
	@Test
	void testStudentsListByCourseIsNotNull() {
		String cursoDeIngles = "C2";
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(cursoDeIngles);
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	void testStudentsListByCourseIsNull() {
		String cursoDeIngles = "B2";
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(cursoDeIngles);
		for(Alumno a: alumnos) {
			alumnoService.deleteStudents(a);
		}
		List<Alumno> alumnosDeCursoBorrados = alumnoService.getStudentsByCourse(cursoDeIngles);
		assertTrue(alumnosDeCursoBorrados.size() == 0);
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
    	Grupo grupo= grupoService.getCourseById("grupo3");       
        alumno1.setGrupos(grupo);
        alumnoService.saveAlumno(alumno1);
		assertTrue(alumno1.getGrupos().getNombreGrupo() == "grupo3");
	}
}

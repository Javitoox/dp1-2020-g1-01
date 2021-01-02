package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class GrupoServiceTest {
	
	@Autowired
	protected GrupoService grupoService;
	
	@Autowired
	protected CursoService cursoService;
	@Autowired
	protected AlumnoService alumnoService;
	
	@Test
	@Transactional
	void createGroup() throws DuplicatedGroupNameException {
		Curso curso = cursoService.getCourseById("B1").get();
		Grupo grupo = new Grupo();
		String name = "GrupoA";
		grupo.setNombreGrupo(name);
		grupo.setCursos(curso);
		grupoService.saveGroup(grupo);
		assertThat(grupo.getNombreGrupo()).isNotNull();
	}
	
	@Test
	void editGroup() throws DuplicatedGroupNameException {
		Grupo gr = grupoService.getGrupo("grupo1").get();
		
		Curso nuevoCurso = new Curso();
		nuevoCurso.setCursoDeIngles(TipoCurso.A1);		
		String newName = "GrupoA";
		
		gr.setNombreGrupo(newName);
		gr.setCursos(nuevoCurso);
		
		grupoService.saveGroup(gr);
		
		assertThat(gr.getNombreGrupo()).isEqualTo(newName);
		assertThat(gr.getCursos()).isEqualTo(nuevoCurso);
	}
	
	@Test
	void deleteGroup() {
		grupoService.deleteGroup("grupo1");
		Optional<Grupo> bg = grupoService.getGrupo("grupo1");
		assertFalse(bg.isPresent());		
	}
	
	
	@Test
	void shouldGetAListWhithGrupos() {
		Set<Grupo> grupos = grupoService.getAllGrupos();
		assertThat(grupos.size()).isGreaterThan(0);	
	}
	
//	@Test
//	void shouldGetAListWithNoGroups() { /*Lo dejo así de momento porque lo borraría :)*/
//		Set<Grupo> grupos = grupoService.getAllGrupos();
//		List<Curso> cursos = cursoService.allCourses();
//		List<Alumno> alumnos = new ArrayList<>();
//		for(Curso c: cursos) {
//			alumnos = alumnoService.getStudentsByCourse(c.getCursoDeIngles());
//			alumnos.forEach(x->alumnoService.deleteStudents(x));
//		}
//		grupos.forEach(x->grupoService.deleteGroup(x.getNombreGrupo()));
//		Set<Grupo> gruposBorrados = grupoService.getAllGrupos();
//		assertTrue(gruposBorrados.size()==0);
//	}
	
//	@Test
//	void shouldFindGroupsByCourse() {
//		List<Grupo> groups = grupoService.getGruposByCourse("B1");
//		assertEquals("B1", groups.get(0).getCursos().getCursoDeIngles().toString());
//	}
}

package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class GrupoServiceTest {
	
	@Autowired
	protected GrupoService grupoService;
	
	@Autowired
	protected CursoService cursoService;
	
	@Test
	@Transactional
	void createGroup() {
		Curso curso = cursoService.getCourseById("B1").get();
		Grupo grupo = new Grupo();
		String name = "GrupoA";
		grupo.setNombreGrupo(name);
		grupo.setCursos(curso);
		grupoService.saveGroup(grupo);
		assertThat(grupo.getNombreGrupo()).isNotNull();
	}
	
	@Test
	void editGroup() {
		Grupo gr = grupoService.getGrupo("grupo1").get();
		
		Curso nuevoCurso = new Curso();
		nuevoCurso.setCursoDeIngles("A1");		
		String newName = "GrupoA";
		
		gr.setNombreGrupo(newName);
		gr.setCursos(nuevoCurso);
		
		grupoService.saveGroup(gr);
		
		assertThat(gr.getNombreGrupo()).isEqualTo(newName);
		assertThat(gr.getCursos()).isEqualTo(nuevoCurso);
	}
	
	@Test
	void deleteGroup() {
		Grupo gr = grupoService.getGrupo("grupo1").get();
		grupoService.deleteGroup(gr);
		Optional<Grupo> bg = grupoService.getGrupo("grupo1");
		assertFalse(bg.isPresent());		
	}
	
	@BeforeAll
	@Test
	void shouldGetAListWhithGrupos() {
		List<Grupo> grupos = grupoService.findAll().stream().collect(Collectors.toList());
		assertThat(grupos.size()).isGreaterThan(0);	
	}
	
	@Test
	void shouldFindGroupsByCourse() {
		List<Grupo> groups = grupoService.getGrupos("B1");
		assertEquals("B1", groups.get(0).getCursos().getCursoDeIngles().toString());
	}
}

package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class GrupoServiceTest {
	
	@Autowired
	protected GrupoService grupoService;
	
	@BeforeAll
	@Test
	void shouldGetAListWhithGrupos() {
		List<Grupo> grupos = grupoService.findAll().stream().collect(Collectors.toList());
		assertThat(grupos.size()).isGreaterThan(0);	
	}
	
	@Test
	void shouldFindGroupsByCourse() {
		List<Grupo> groups = grupoService.getGrupo("B1");
		assertEquals("B1", groups.get(0).getCursos().getCursoDeIngles().toString());
	}
}

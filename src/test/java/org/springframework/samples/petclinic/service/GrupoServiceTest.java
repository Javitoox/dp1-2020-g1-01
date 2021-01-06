package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.GrupoRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GrupoServiceTest {
	
	private static Set<Grupo> emptyGroups;	
	private static Set<Grupo> notEmptyGroups;	
	
	private static List<Grupo> gruposPorCurso;	
	private static final TipoCurso CURSO= TipoCurso.B1;
	private static Grupo g;
	
	@Mock
	private GrupoRepository grupoRepository;
	protected GrupoService grupoService;

	
	@BeforeAll
	void data() {
		g = new Grupo();
		g.setNombreGrupo("Grupo A");
		emptyGroups = new HashSet<>();
		notEmptyGroups = new HashSet<>();
		notEmptyGroups.add(g);
		gruposPorCurso = new ArrayList<>();
		gruposPorCurso.add(g);
	}
	
	@BeforeEach
	void setup() {
		grupoService = new GrupoService(grupoRepository);
	}
	
	@Test
	void shouldShowAGroupListIsNotEmpty() {
		when(grupoRepository.findAll()).thenReturn(notEmptyGroups);
		assertThat(grupoService.getAllGrupos()).isNotEmpty();
	}
	
	@Test
	void shouldShowGroupListIsEmpty() {
		when(grupoRepository.findAll()).thenReturn(emptyGroups);
		assertThat(grupoService.getAllGrupos()).isEmpty();

	}
	 
	@Test 
	void shouldShowListGroupByCourseIsNotEmpty() {
		when(grupoRepository.findByCurso(CURSO)).thenReturn(gruposPorCurso);
		assertThat(grupoService.getGruposByCourse(CURSO)).isNotEmpty();
	}
	
	@Test
	@Transactional
	void shoudCreateGroup() throws DuplicatedGroupNameException {
		Grupo gg = new Grupo();
		Curso c = new Curso();
		c.setCursoDeIngles(CURSO);
		gg.setNombreGrupo("Grupo A");
		gg.setCursos(c);
		
		grupoService.saveGroup(gg);
		
		verify(grupoRepository, times(1)).save(any());
	}
	
	@Test
	@Transactional
	void shouldDeleteGroup() {
		Grupo gg = new Grupo();
		String name = "Grupo A";
		gg.setNombreGrupo(name);
		grupoService.deleteGroup(name);
		
		verify(grupoRepository, times(1)).deleteById(any());

	}

	 
}

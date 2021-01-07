package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GrupoServiceTest {
	
	private static Set<Grupo> notEmptyGroups;	

	private static List<String> nombresGruposPorCurso;	
	private static List<String> nombresGruposVacios;	
	private static final String NOMBRE_GRUPO = "Grupo A";
	private static final TipoCurso CURSO= TipoCurso.B1;
	private static Grupo g;
	
	@Mock
	private GrupoRepository grupoRepository;
	protected GrupoService grupoService;

	
	@BeforeAll
	void data() {
		g = new Grupo();
		g.setNombreGrupo(NOMBRE_GRUPO);
		nombresGruposVacios = new ArrayList<>();
		nombresGruposVacios.add(NOMBRE_GRUPO);
		
		notEmptyGroups = new HashSet<>();
		notEmptyGroups.add(g);
		
		nombresGruposPorCurso = new ArrayList<>();
		nombresGruposPorCurso.add(g.getNombreGrupo());
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
	void shouldReturnAllEmptyGroups() {
		when(grupoRepository.findAllEmptyGroups()).thenReturn(nombresGruposVacios);
		assertThat(grupoService.getEmptyGroups()).isNotEmpty();

	}
	 
	@Test 
	void shouldShowListNamesGroupsByCourseIsNotEmpty() {
		when(grupoRepository.findNameByCurso(CURSO)).thenReturn(nombresGruposPorCurso);
		assertThat(grupoService.getNameGruposByCourse(CURSO)).isNotEmpty();
	}
	
	
	@Test
	@Transactional
	void shoudCreateGroup(){
		Grupo gg = new Grupo();
		Curso c = new Curso();
		c.setCursoDeIngles(CURSO);
		gg.setNombreGrupo(NOMBRE_GRUPO);
		gg.setCursos(c);
		
		grupoService.saveGroup(gg);
		
		verify(grupoRepository).save(gg);
	}
	
	@Test
	@Transactional
	void shouldDeleteGroup() {
		Grupo gg = new Grupo();
		String name = "Grupo A";
		gg.setNombreGrupo(name);
		grupoService.deleteGroup(name);
		
		verify(grupoRepository).deleteById(name);

	}

	 
}

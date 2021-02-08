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
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.repository.GrupoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GrupoServiceTests {

	private static Set<Grupo> notEmptyGroups;
	private static Set<Grupo> emptyGroups;


	private static List<String> nombresGruposPorCurso;
	private static List<String> nombresGruposVacios;
	private static final String NOMBRE_GRUPO = "Grupo A";
	private static final String CURSO= "B1";
	private static Grupo g;

	@Mock
	private GrupoRepository grupoRepository;

	@Mock
	private AlumnoService alumnoService;
	private GrupoService grupoService;


	@BeforeAll
	void data() {
		g = new Grupo();
		g.setNombreGrupo(NOMBRE_GRUPO);
		nombresGruposVacios = new ArrayList<>();
		nombresGruposVacios.add(NOMBRE_GRUPO);

		emptyGroups = new HashSet<>();

		notEmptyGroups = new HashSet<>();
		notEmptyGroups.add(g);

		nombresGruposPorCurso = new ArrayList<>();
		nombresGruposPorCurso.add(g.getNombreGrupo());
	}

	@BeforeEach
	void setup() {
		grupoService = new GrupoService(grupoRepository, alumnoService);
	}

	@Test
	void shouldShowAGroupListIsEmpty() {
		when(grupoRepository.findAll()).thenReturn(emptyGroups);
		assertThat(grupoService.getAllGrupos()).isEmpty();
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
	void shouldDeleteGroup() {
		Grupo gg = new Grupo();
		String name = "Grupo A";
		gg.setNombreGrupo(name);
		grupoService.deleteGroup(name);

		verify(grupoRepository).deleteById(name);

	}

	@Test
	void shouldReturnTrueIfAGroupExists() {
		when(grupoRepository.existsById(NOMBRE_GRUPO)).thenReturn(true);
		assertThat(grupoService.exists(NOMBRE_GRUPO)).isTrue();
	}

	@Test
	void shouldReturnFalseIfAGroupDoesntExists() {
		when(grupoRepository.existsById(NOMBRE_GRUPO)).thenReturn(false);
		assertThat(grupoService.exists(NOMBRE_GRUPO)).isFalse();
	}

	@Test
	void shoulReturnTheCourseNameOfAGroup() {
		List<String> coursesByGroup = new ArrayList<String>();
		Grupo g = new Grupo();
		g.setNombreGrupo(NOMBRE_GRUPO);
		coursesByGroup.add(g.getNombreGrupo());
		when(grupoRepository.findNameByGrupo(NOMBRE_GRUPO)).thenReturn(coursesByGroup);
		assertThat(grupoService.getCursoByGrupo(NOMBRE_GRUPO).size()).isGreaterThan(0);
	}

	@Test
	void shouldReturnAllGroupNames() {
		List<String> allNames = new ArrayList<String>();
		String name2 = "GrupoB2";
		allNames.add(g.getNombreGrupo());
		allNames.add(name2);
		when(grupoRepository.findAllGroupNames()).thenReturn(allNames);
		assertThat(grupoService.getGroupNames()).isNotEmpty();
		assertThat(grupoService.getGroupNames().size()).isEqualTo(2);

	}

	@Test
	void shouldReturnTheTotalAlumnsOfAGroup() {
		List<Alumno> numAlumnos = new ArrayList<>();
		Alumno a = new Alumno();
		Alumno a1 = new Alumno();
		numAlumnos.add(a); numAlumnos.add(a1);
		when(grupoRepository.numAlumnosGrupo(NOMBRE_GRUPO)).thenReturn(numAlumnos);
		assertThat(grupoService.numAlumnos(NOMBRE_GRUPO)).isEqualTo(2);
	}

	@Test
	void shoulReturnTrueIfAGroupIsNotEmpty() {
		List<Alumno> alumnos = new ArrayList<>();
		Alumno a = new Alumno();
		Alumno a1 = new Alumno();
		alumnos.add(a); alumnos.add(a1);
		when(alumnoService.getStudentsPerGroup(NOMBRE_GRUPO)).thenReturn(alumnos);
		assertThat(grupoService.grupoVacio(NOMBRE_GRUPO)).isFalse();
	}

	@Test
	void shoulReturnTrueIfAGroupIsEmpty() {
		List<Alumno> alumnos = new ArrayList<>();
		when(alumnoService.getStudentsPerGroup(NOMBRE_GRUPO)).thenReturn(alumnos);
		assertThat(grupoService.grupoVacio(NOMBRE_GRUPO)).isTrue();
	}

	@Test
	void shoulReturnFreeGroupsToAsignToAStudent() {
		List<String> ls = new ArrayList<>();
		String name1 = "Grupo1";
		String name2 = "Grupo2";
		ls.add(name1);
		ls.add(name2);
		when(grupoRepository.findGroupsToAssign("Fernando")).thenReturn(ls);
		assertThat(grupoService.getAssignableGroupsByStudent("Fernando")).isNotEmpty();
		assertThat(grupoService.getAssignableGroupsByStudent("Fernando").size()).isEqualTo(2);
	}

}

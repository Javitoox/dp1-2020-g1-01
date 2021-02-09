package org.springframework.samples.tea.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.model.Grupo;

@DataJpaTest
public class GrupoRepositoryTests {

	private static Grupo grupo;
	private static final String CURSO= "B1";

	@Autowired
	protected GrupoRepository grupoRepository;

    @Autowired
    protected CursoRepository cursoRepository;

	@Autowired
	protected AsignacionProfesorRepository asignacionPrRepository;

	@BeforeEach
	void setup() {
		grupo = new Grupo();
		grupo.setNombreGrupo("Grupo 1");
		Curso c = cursoRepository.findById(CURSO).get();
		grupo.setCursos(c);

		Grupo g = new Grupo();
		g.setNombreGrupo("Grupo H");
		g.setCursos(c);
		grupoRepository.save(g);


		List<Alumno> alumnos = new ArrayList<>();
		Alumno a = new Alumno();
		a.setNickUsuario("Bebelyn");
		a.setNombreCompletoUsuario("Evelyn Yugsi");
		a.setCorreoElectronicoUsuario("bebelyn@gmail.com");
		a.setFechaNacimiento(LocalDate.of(1999, 11, 22));
		a.setDniUsuario("11111111A");
		a.setNumTelefonoUsuario("611111111");
		a.setDireccionUsuario("Macarena");
		a.setContraseya("EvelynP091");
		a.setGrupos(grupo);

		Alumno a1 = new Alumno();
		a1.setNickUsuario("Javiel");
		a1.setNombreCompletoUsuario("Javier Vila");
		a1.setCorreoElectronicoUsuario("javiel@gmail.com");
		a1.setFechaNacimiento(LocalDate.of(2000, 11, 22));
		a1.setDniUsuario("11111111B");
		a1.setNumTelefonoUsuario("611111112");
		a1.setDireccionUsuario("Triana");
		a1.setContraseya("Javiel123K");
		a1.setGrupos(grupo);

		alumnos.add(a); alumnos.add(a1);
		grupo.setAlumnos(alumnos);

	}

	@Test
	void shouldReturnAllGroupNames() {
		grupoRepository.save(grupo);
		List<String> allGroupNames = grupoRepository.findAllGroupNames();
		assertThat(allGroupNames).isNotEmpty();
	}


	@Test
	void shouldReturnGroupNamesByCourse() {
		List<String> allGroupNamesByCourse = grupoRepository.findNameByCurso(CURSO);
		assertThat(allGroupNamesByCourse).isNotEmpty();
	}

	@Test
	void shouldReturnAllCoursesByGroup() {
		grupoRepository.save(grupo);
		List<String> cursoPorGrupo = grupoRepository.findNameByGrupo(grupo.getNombreGrupo());
		assertThat(cursoPorGrupo).isNotEmpty();
	}

	@Test
	void shouldReturnAllEmptyGroupNames() {
		List<String> gruposVacios = grupoRepository.findAllEmptyGroups();
		assertThat(gruposVacios).isNotEmpty();
	}

	@Test
	void shouldReturnAllGroupAlumns() {
		grupoRepository.save(grupo);
		List<Alumno> alumnos = grupoRepository.numAlumnosGrupo(grupo.getNombreGrupo());
		assertThat(alumnos).isNotEmpty();
	}

	@Test
	void shoulReturnAllGroupNamesToAssignByStudent() {
		List<String> grupoPorEstudiante = grupoRepository.findGroupsToAssign("Bebelyn");
		assertThat(grupoPorEstudiante.size()).isGreaterThan(0);
		assertThat(grupoPorEstudiante).isNotEmpty();

	}
}


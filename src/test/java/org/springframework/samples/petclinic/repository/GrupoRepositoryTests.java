package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;

@DataJpaTest
public class GrupoRepositoryTests {
	
	private static Grupo grupo;
	private static final TipoCurso CURSO= TipoCurso.B1;
	
	@Autowired
	protected GrupoRepository grupoRepository;
	
	@Autowired
	protected AsignacionProfesorRepository asignacionPrRepository;
	
	@BeforeEach
	void setup() {
		grupo = new Grupo();
		grupo.setNombreGrupo("Grupo 1");
		
		Curso c = new Curso();
		c.setCursoDeIngles(CURSO);
		
		grupo.setCursos(c);
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
		grupoRepository.save(grupo);

	}
	
	@Test
	void shouldReturnAllGroupNames() {
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
		List<Alumno> alumnos = grupoRepository.numAlumnosGrupo(grupo.getNombreGrupo());
		assertThat(alumnos).isNotEmpty();
	}
}


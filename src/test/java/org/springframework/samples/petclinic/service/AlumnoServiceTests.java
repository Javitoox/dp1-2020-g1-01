package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.repository.AlumnoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AlumnoServiceTests {
	
	private static List<Alumno> alumnosNotEmpty;	
	private static List<Alumno> alumnosEmpty;	
 
	private static final TipoCurso CURSO_NOT_EMPTY = TipoCurso.B1;
	private static final TipoCurso CURSO_EMPTY = TipoCurso.C2;
	
	private static final String TUTOR_WITH_STUDENTS= "PedroGar";
	private static final String TUTOR_WITHOUT_STUDENTS= "Manuel12";

	private static Grupo emptyGroup;
	private static Grupo notEmptyGroup; 

	@Mock
	private AlumnoRepository alumnoRepository;
	
	protected AlumnoService alumnoService;
		
	@BeforeAll
	void data() { 
		emptyGroup = new Grupo();
		emptyGroup.setNombreGrupo("Grupo A");
		notEmptyGroup = new Grupo();
		notEmptyGroup.setNombreGrupo("Grupo B");
		
		Alumno a = new Alumno();
		a.setGrupos(notEmptyGroup);
		
		alumnosNotEmpty= new ArrayList<Alumno>();
		alumnosNotEmpty.add(a);
		
		alumnosEmpty= new ArrayList<Alumno>();	
	
	}
	
	@BeforeEach
	void setup() {
		alumnoService= new AlumnoService(alumnoRepository);
	}
	
	
	@Test
	void shouldShowStudentsListIsNotEmpty() {
		when(alumnoRepository.findStudents()).thenReturn(alumnosNotEmpty);
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldShowStudentsListIsEmpty() {
		when(alumnoRepository.findStudents()).thenReturn(alumnosEmpty);
		List<Alumno> alumnos = alumnoService.getAllAlumnos();
		assertThat(alumnos.size()).isEqualTo(0);
	} 
	
	@Test
	void shouldShowStudentsListByCourseIsNotNull() {
		when(alumnoRepository.findStudentsByCourse(any(TipoCurso.class))).thenReturn(alumnosNotEmpty);
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(CURSO_NOT_EMPTY);
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldShowStudentsListByCourseIsNull() {
		when(alumnoRepository.findStudentsByCourse(any(TipoCurso.class))).thenReturn(alumnosEmpty);
		List<Alumno> alumnos = alumnoService.getStudentsByCourse(CURSO_EMPTY);
		assertThat(alumnos.size()).isEqualTo(0);
	}
	
	
	@Test 
	void shouldShowStudentsByTutorIsNotNull() {
		when(alumnoRepository.findStudentsByTutor(any(String.class))).thenReturn(alumnosNotEmpty);
		List<Alumno> alumnos = alumnoService.getAllMyStudents(TUTOR_WITH_STUDENTS);
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test 
	void shouldShowStudentsByTutorIsNull() {
		when(alumnoRepository.findStudentsByTutor(any(String.class))).thenReturn(alumnosEmpty);
		List<Alumno> alumnos = alumnoService.getAllMyStudents(TUTOR_WITHOUT_STUDENTS);
		assertThat(alumnos.size()).isEqualTo(0);
	}
	
	@Test
	void shouldShowAStudentListByGroupIsNotEmpty() {
		String name = notEmptyGroup.getNombreGrupo();
		when(alumnoRepository.findByGroup(name)).thenReturn(alumnosNotEmpty);
		assertThat(alumnoService.getStudentsPerGroup(name)).isNotEmpty();
	}
	
	@Test
	void shouldShowAStudentListByGroupIsEmpty() {
		String name = emptyGroup.getNombreGrupo();
		when(alumnoRepository.findByGroup(name)).thenReturn(alumnosEmpty);
		assertThat(alumnoService.getStudentsPerGroup(name)).isEmpty();
	}
	
	@Test
	void shouldUpdateAGroupAlumn() {
		Alumno a = new Alumno();
		Grupo g = new Grupo();
		a.setGrupos(g);
		
		alumnoService.saveAlumno(a);
		
		verify(alumnoRepository, times(1)).save(any());
	 }
	
	
	
//	@Autowired
//	protected AlumnoService alumnoService;
//	
//	@Autowired
//	protected GrupoService grupoService;
//	
//	@Autowired
//	protected CursoService cursoService;
//
//	@BeforeEach
//	@Transactional
//	void insertAlumno() throws DuplicatedGroupNameException {
//		Alumno alumno = new Alumno();
//		alumno.setContraseya("HolaBuenas777");
//		alumno.setCorreoElectronicoUsuario("javikuka7@gmail.com");
//		alumno.setDireccionUsuario("Calle error");
//		alumno.setDniUsuario("76766776Y");
//		alumno.setFechaMatriculacion(LocalDate.now());
//		alumno.setFechaSolicitud(LocalDate.now());
//		alumno.setFechaNacimiento(LocalDate.of(2000, 06, 22));
//		alumno.setNickUsuario("JaviMartinez");
//		alumno.setNombreCompletoUsuario("Javi Martínez");
//		alumno.setNumTelefonoUsuario("635096767");
//		alumno.setNumTareasEntregadas(4);
//		
//		Curso curso = new Curso();
//		curso.setCursoDeIngles("C2");
//		
//		Grupo grupo = new Grupo();
//		grupo.setCursos(curso);
//		grupo.setNombreGrupo("grupo1_B2");
//		grupoService.saveGroup(grupo);
//		
//		alumno.setGrupos(grupo);
//		alumnoService.saveAlumno(alumno);
//	}
//	
//	@Test
//	void testListWithAlumnos() {/*no sería mejor comprobar el tamaño de la lista de alumnos antes y despues de insertar uno nuevo?? */
//		List<Alumno> alumnos = alumnoService.getAllAlumnos();
//		assertThat(alumnos.size()).isGreaterThan(0);
//	}
//	

}

package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AlumnoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AlumnoControllerTests {

	@MockBean
	private AlumnoService alumnoService;

	@MockBean
	private GrupoService grupoService;

	@Autowired
	private MockMvc mockMvc;

	private Alumno alumno;
	private Grupo grupo;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@BeforeAll
	void setup() {
		Curso curso = new Curso();
		curso.setCursoDeIngles(TipoCurso.A1);
		grupo = new Grupo();
		grupo.setCursos(curso);
		grupo.setNombreGrupo("Grupo C4");
		alumno = new Alumno();
		alumno.setGrupos(grupo);
		alumno.setNickUsuario("EvelynYY");
		alumno.setContraseya("EveOLka1287");
		alumno.setDniUsuario("45128487Y");
		alumno.setNombreCompletoUsuario("Evelyn Yugsi");
		alumno.setCorreoElectronicoUsuario("evelyn@gmail.com");
		alumno.setNumTelefonoUsuario("68749857");
		alumno.setDireccionUsuario("Calle Papa");
		alumno.setFechaNacimiento(LocalDate.parse("1999-08-13"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShouldEditStudent() throws Exception {
		mockMvc.perform(put("/alumnos/editStudent").param("nickUsuario", "Javi").param("contraseya", "Bebesita7")
				.param("dniUsuario", "55635286A").param("nombreCompletoUsuario", "Javi Martínez")
				.param("correoElectronicoUsuario", "martinez@gmail.com").param("numTelefonoUsuario", "626222111")
				.param("numTelefonoUsuario2", "665768567").param("direccionUsuario", "Calle El Punto Medio")
				.param("fechaNacimiento", "2000/10/15").with(csrf()).sessionAttr("type", "profesor")).andDo(print())
				.andDo(print()).andExpect(status().isOk());

	}

	@WithMockUser(value = "spring")
	@Test
	void testShouldntEditStudentBecauseUnauthorized() throws Exception {
		mockMvc.perform(put("/alumnos/editStudent").param("nickUsuario", "Javi").param("contraseya", "Bebesita7")
				.param("dniUsuario", "55635286A").param("nombreCompletoUsuario", "Javi Martínez")
				.param("correoElectronicoUsuario", "martinez@gmail.com").param("numTelefonoUsuario", "626222111")
				.param("numTelefonoUsuario2", "665768567").param("direccionUsuario", "Calle El Punto Medio")
				.param("fechaNacimiento", "2000/10/15").with(csrf()).sessionAttr("type", "alumno")).andDo(print())
				.andDo(print()).andExpect(status().isOk());

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowStudentInfo() throws Exception {
		given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(get("/alumnos/getStudentInfo/manolo")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowListAllStudents() throws Exception {
		given(this.alumnoService.getAllAlumnos()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/all")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowListStudentsByCourse() throws Exception {
		given(this.alumnoService.getStudentsByCourse(any(TipoCurso.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/getByCourse/B1")).andExpect(status().isOk());
	}

	@WithMockUser(value = "marrambla2", roles = "tutor")
	@Test
	void testShowListStudentsByTutor() throws Exception {
		given(this.alumnoService.getAllMyStudents(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/marrambla2/allMyStudents")).andExpect(status().isOk());
	}

	@WithMockUser(value = "pepito", roles = "tutor")
	@Test
	void testShowListStudentsByTutorNotEqualsThanLoguedUser() throws Exception {
		given(this.alumnoService.getAllMyStudents(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/marrambla2/allMyStudents")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllStudentsByGroup() throws Exception {
		Alumno a = new Alumno();
		List<Alumno> alumnos = new ArrayList<>();
		alumnos.add(a);
		given(this.alumnoService.getStudentsPerGroup(any(String.class))).willReturn(alumnos);
		mockMvc.perform(get("/alumnos/GrupoA")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllStudentsNamesAbleToDelete() throws Exception {
		List<String> alumnos = new ArrayList<>();
		String name = "Lola Flores";
		alumnos.add(name);
		given(this.alumnoService.getStudentsToDelete()).willReturn(alumnos);
		mockMvc.perform(get("/alumnos/ableToDelete")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllStudentsNamesWithNoGroups() throws Exception {
		given(this.alumnoService.getStudentsWithNoGroups()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/studentsWithNoGroups")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateGroup() throws Exception {
		mockMvc.perform(put("/alumnos/assignStudent/{nickUsuario}/{nombreGrupo}", alumno.getNickUsuario(), "Grupo2")
				.with(csrf())).andExpect(status().isCreated());
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateGroupFail() throws Exception {
		given(this.grupoService.numAlumnos("Grupo2")).willReturn(12);
		mockMvc.perform(put("/alumnos/assignStudent/{nickUsuario}/{nombreGrupo}", alumno.getNickUsuario(), "Grupo2")
				.with(csrf())).andExpect(status().isAlreadyReported());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteStudentError() throws Exception{
		mockMvc.perform(delete("/alumnos/delete/{nickUsuario}","EvelynYY").with(csrf()))
		.andExpect(status().isBadRequest());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteStudentOk() throws Exception{
		List<String> paraBorrar = new ArrayList<>();
		paraBorrar.add(alumno.getNickUsuario());
		given(this.alumnoService.getStudentsToDelete()).willReturn(paraBorrar);
		mockMvc.perform(delete("/alumnos/delete/{nickUsuario}", alumno.getNickUsuario()).with(csrf()))
		.andExpect(status().isOk());
	}
	

}
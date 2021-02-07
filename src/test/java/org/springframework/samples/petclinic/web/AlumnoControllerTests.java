package org.springframework.samples.petclinic.web;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
 
@WebMvcTest(controllers=AlumnoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AlumnoControllerTests {

	@MockBean
	private AlumnoService alumnoService;
	
	@MockBean
	private GrupoService grupoService;
	
	@Autowired
	private MockMvc mockMvc;
	

	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldEditStudent() throws Exception {	 
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setDniUsuario("45676787Y");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setNumTareasEntregadas(3);
		alumno.setNumTelefonoUsuario("677676676");
		mockMvc.perform(put("/alumnos/editStudent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(alumno.toJson())
				.with(csrf()))
		.andExpect(status().isCreated());
	}
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldntEditStudentUnauthorized() throws Exception {	 
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setDniUsuario("45676787Y");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setNumTareasEntregadas(3);
		alumno.setNumTelefonoUsuario("6776766764234234234234");
		mockMvc.perform(put("/alumnos/editStudent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(alumno.toJson())
				.with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation());
	}
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldEditPersonalInfo() throws Exception {	 
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setDniUsuario("45676787Y");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setNumTareasEntregadas(3);
		alumno.setNumTelefonoUsuario("622119555");
		mockMvc.perform(put("/alumnos/editPersonalInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(alumno.toJson())
				.with(csrf()))
		.andExpect(status().isCreated());
	}
	
	@WithMockUser(value = "spring")
	@Test 
	void testShoulntdEditPersonalInfoNonAuth() throws Exception {	 
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setDniUsuario("45676787Y");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setNumTareasEntregadas(3);
		alumno.setNumTelefonoUsuario("62211955532423432423423");
		mockMvc.perform(put("/alumnos/editPersonalInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(alumno.toJson())
				.with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation());
	}
	
	@WithMockUser(value  = "manolo", authorities= {"alumno"})
	@Test
	void testShowStudentInfo() throws Exception {
		given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(get("/alumnos/getStudentInfo/manolo")).andExpect(status().isOk());
	}
	
	@WithMockUser(value  = "manolo", authorities= {"alumno"})
	@Test
	void testShowStudentInfoUnauthorized() throws Exception {
		given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(get("/alumnos/getStudentInfo/Javi")).andExpect(status().isUnauthorized());
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
	
	@WithMockUser(username = "marrambla2", authorities ={"tutor"})	
	@Test
	void testShowListStudentsByTutor() throws Exception {
		given(this.alumnoService.getAllMyStudents(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/marrambla2/allMyStudents")).andExpect(status().isOk());
	}
	
	@WithMockUser(username = "pepito", authorities ={"tutor"})	
	@Test
	void testShowListStudentsByTutorNotEqualsThanLoguedUser() throws Exception {
		given(this.alumnoService.getAllMyStudents(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/marrambla2/allMyStudents")).andExpect(status().isUnauthorized());
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAStudentsListByGroupIfLoggedAsTeacher() throws Exception{
		given(this.alumnoService.getStudentsPerGroup(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/GrupoA").sessionAttr("type", "profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAStudentsListByGroupIfLoggedAsAlumn() throws Exception{
		given(this.alumnoService.getStudentsPerGroup(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/GrupoA").sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
	}
	
}
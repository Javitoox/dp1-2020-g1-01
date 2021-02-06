package org.springframework.samples.petclinic.web;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
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
		  mockMvc.perform(put("/alumnos/editStudent")
				  .param("nickUsuario", "Javi")
				  .param("contraseya", "Bebesita7")
				  .param("dniUsuario", "55635286A")
				  .param("nombreCompletoUsuario", "Javi Martínez")
				  .param("correoElectronicoUsuario", "martinez@gmail.com")
				  .param("numTelefonoUsuario", "626222111")
				  .param("numTelefonoUsuario2", "665768567")
				  .param("direccionUsuario", "Calle El Punto Medio")
				  .param("fechaNacimiento", "2000/10/15")
				  .with(csrf())		  
				  .sessionAttr("type","profesor")).andDo(print())
		  .andDo(print())
		  .andExpect(status().isOk());
	  
	  } 
	
	@WithUserDetails
	@Test 
	void testShouldntEditStudentBecauseUnauthorized() throws Exception {		     
		  mockMvc.perform(put("/alumnos/editStudent")
				  .param("nickUsuario", "Javi")
				  .param("contraseya", "Bebesita7")
				  .param("dniUsuario", "55635286A")
				  .param("nombreCompletoUsuario", "Javi Martínez")
				  .param("correoElectronicoUsuario", "martinez@gmail.com")
				  .param("numTelefonoUsuario", "626222111")
				  .param("numTelefonoUsuario2", "665768567")
				  .param("direccionUsuario", "Calle El Punto Medio")
				  .param("fechaNacimiento", "2000/10/15")
				  .with(csrf())		  
				  .sessionAttr("type","alumno")).andDo(print())
		  .andDo(print())
		  .andExpect(status().isOk());
	  
	  } 
	
	@WithMockUser(value = "spring")
	@Test
	void testShowStudentInfo() throws Exception {
		given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(get("/alumnos/getStudentInfo/manolo")).andExpect(status().isOk());
	}
	
	/*
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testShowStudentInfoWhenNotLoggedAsProfessor() throws Exception {
	 * mockMvc.perform(get("/alumnos/getStudentInfo/manolo")).andExpect(status().
	 * isUnauthorized()); }
	 */
	
	@WithMockUser(value = "spring")
	@Test
	void testShowListAllStudents() throws Exception {
		given(this.alumnoService.getAllAlumnos()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/all")).andExpect(status().isOk());
	}
	
	/*
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testNotShowListAllStudents() throws Exception {
	 * mockMvc.perform(get("/alumnos/all").sessionAttr("type","alumno")).andExpect(
	 * status().isUnauthorized()); }
	 */
	
	@WithMockUser(value = "spring")
	@Test
	void testShowListStudentsByCourse() throws Exception {
		given(this.alumnoService.getStudentsByCourse(any(TipoCurso.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/getByCourse/B1")).andExpect(status().isOk());
	}
	
	/*
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testNotShowListStudentsByCourse() throws Exception {
	 * mockMvc.perform(get("/alumnos/getByCourse/B1")).andExpect(status().
	 * isUnauthorized()); }
	 */
	@WithUserDetails(value = "marrambla2", userDetailsServiceBeanName = "loadUserByUsername")
	@Test
	void testShowListStudentsByTutor() throws Exception {
		given(this.alumnoService.getAllMyStudents(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/alumnos/marrambla2/allMyStudents")).andExpect(status().isOk());
	}
	
	/*
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testNotShowListStudentsByTutorNotLoggedAsTutor() throws Exception
	 * {
	 * mockMvc.perform(get("/alumnos/marrambla2/allMyStudents").sessionAttr("type",
	 * "alumno").sessionAttr("nickUsuario",
	 * "marrambla3")).andExpect(status().isUnauthorized()); }
	 * 
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testNotShowListStudentsByTutorNotLoggedWithNameRequired() throws
	 * Exception {
	 * mockMvc.perform(get("/alumnos/marrambla3/allMyStudents").sessionAttr("type",
	 * "tutor").sessionAttr("nickUsuario",
	 * "marrambla2")).andExpect(status().isUnauthorized()); }
	 */
	
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
package org.springframework.samples.petclinic.web;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;

@WebMvcTest(controllers=AlumnoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AlumnoControllerTests {

	
	@MockBean
	private AlumnoService alumnoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AlumnoController alumnoController;
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testShowStudentInfoWhenLoggedAsProfessor() throws Exception {
		given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(get("/alumnos/getStudentInfo/manolo").sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowStudentInfoWhenNotLoggedAsProfessor() throws Exception {
		given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(get("/alumnos/getStudentInfo/manolo").sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	
	
}

package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AuthController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AuthControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username = "JaviMarFer", authorities = {"alumno"}) // con una prueba de auth es suficiente
	@Test
	void testAuthAlumno() throws Exception {		
		mockMvc.perform(get("/basicauth"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("alumno")));
	}
	
//	@WithMockUser(username = "JaviMarFer", authorities = {"profesor"})
//	@Test
//	void testAuthProfesor() throws Exception {		
//		mockMvc.perform(get("/basicauth"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$", is("profesor")));
//	}
//	
//	@WithMockUser(username = "JaviMarFer", authorities = {"tutor"})
//	@Test
//	void testAuthTutor() throws Exception {		
//		mockMvc.perform(get("/basicauth"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$", is("tutor")));
//	}
	
	@Test
	void testNotAuth() throws Exception {		
		mockMvc.perform(get("/basicauth"))
		.andExpect(status().isUnauthorized());
	}

}

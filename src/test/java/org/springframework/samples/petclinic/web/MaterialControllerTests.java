package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.MaterialService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=MaterialController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MaterialControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MaterialService materialService;

	
	@Test
	@WithMockUser(username = "maribel", authorities = "profesor")
	void testShouldGetMaterialByProfesor() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByProfesor/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());		
	}

	@Test
	@WithMockUser(username = "antonio", authorities = "profesor")
	void testShouldNotGetMaterialByProfesor() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByProfesor/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isUnauthorized());		
	}
	
	@Test
	@WithMockUser(username = "maribel", authorities = "alumno")
	void testShouldGetMaterialByStudent() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByAlumno/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());		
	}
	
	@Test
	@WithMockUser(username = "antonio", authorities = "alumno")
	void testShouldNotGetMaterialByStudent() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByAlumno/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isUnauthorized());		
	}
	
	
}
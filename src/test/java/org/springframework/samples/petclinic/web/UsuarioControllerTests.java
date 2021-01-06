package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.hamcrest.Matchers.*;

@Slf4j
@WebMvcTest(controllers = UsuarioController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class UsuarioControllerTests {
	
	private static final String NICK = "JaviMartinez77";
	private static final String PASSWORD = "Testing887";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@WithMockUser(value = "spring")
	@Test
	void testAuthentication() throws Exception{
		mockMvc.perform(get("/auth").sessionAttr("type", "alumno"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("alumno")));
	}

}

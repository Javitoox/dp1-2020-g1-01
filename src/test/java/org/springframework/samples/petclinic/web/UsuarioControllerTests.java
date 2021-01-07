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
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


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
	void testLoginUser() throws Exception{
		given(usuarioService.typeOfUser(NICK, PASSWORD)).willReturn("profesor");
		
		mockMvc.perform(get("/login").queryParam("username", NICK).queryParam("password", PASSWORD))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("profesor")));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testLoginUsernameNotExist() throws Exception{
		given(usuarioService.typeOfUser(NICK, PASSWORD)).willReturn("Username not exist");
		
		mockMvc.perform(get("/login").queryParam("username", NICK).queryParam("password", PASSWORD))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("Username not exist")));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testLoginIncorrectPassword() throws Exception{
		given(usuarioService.typeOfUser(NICK, PASSWORD)).willReturn("Incorrect password");
		
		mockMvc.perform(get("/login").queryParam("username", NICK).queryParam("password", PASSWORD))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("Incorrect password")));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAuthenticationWithAttributeType() throws Exception{
		mockMvc.perform(get("/auth").sessionAttr("type", "alumno"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("alumno")));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAuthenticationSessionNull() throws Exception{
		mockMvc.perform(get("/auth"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("usuario")));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAuthenticationAttributeNull() throws Exception{
		mockMvc.perform(get("/auth").sessionAttr("other", "other"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$", is("usuario")));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testLogout() throws Exception{
		mockMvc.perform(delete("/logout").with(csrf()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("Succesfull logout")));
	}

}

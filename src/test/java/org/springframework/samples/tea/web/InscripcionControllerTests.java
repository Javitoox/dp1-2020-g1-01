package org.springframework.samples.tea.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.service.InscripcionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= InscripcionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class InscripcionControllerTests {

	@MockBean
	private InscripcionService inscripcionService;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "JaviMarFer", authorities = {"alumno"})
	@Test
	void testJoinInEvent() throws Exception {
		given(inscripcionService.joinOrDisjoin(1, "JaviMarFer", true)).willReturn(true);

		mockMvc.perform(put("/inscriptions/join/{id}/{nick}", "1", "JaviMarFer").with(csrf()))
		.andExpect(status().isOk());
	}

	@WithMockUser(username = "JaviMarFer", authorities = {"alumno"})
	@Test
	void testJoinInEventIncorret() throws Exception {
		given(inscripcionService.joinOrDisjoin(1, "JaviMarFer", true)).willReturn(false);

		mockMvc.perform(put("/inscriptions/join/{id}/{nick}", "1", "JaviMarFer").with(csrf()))
		.andExpect(status().isNotFound());
	}

	@WithMockUser(username = "Javi", authorities = {"alumno"})
	@Test
	void testJoinInEventUnauthorized() throws Exception {
		mockMvc.perform(put("/inscriptions/join/{id}/{nick}", "1", "JaviMarFer").with(csrf()))
		.andExpect(status().isUnauthorized());
	}

	@WithMockUser(username = "JaviMarFer", authorities = {"alumno"})
	@Test
	void testDisjoinInEvent() throws Exception {
		given(inscripcionService.joinOrDisjoin(1, "JaviMarFer", false)).willReturn(true);

		mockMvc.perform(put("/inscriptions/disjoin/{id}/{nick}", "1", "JaviMarFer").with(csrf()))
		.andExpect(status().isOk());
	}

	@WithMockUser(username = "JaviMarFer", authorities = {"alumno"})
	@Test
	void testDisjoinInEventIncorret() throws Exception {
		given(inscripcionService.joinOrDisjoin(1, "JaviMarFer", false)).willReturn(false);

		mockMvc.perform(put("/inscriptions/disjoin/{id}/{nick}", "1", "JaviMarFer").with(csrf()))
		.andExpect(status().isNotFound());
	}

	@WithMockUser(username = "Javi", authorities = {"alumno"})
	@Test
	void testDisjoinInEventUnauthorized() throws Exception {
		mockMvc.perform(put("/inscriptions/disjoin/{id}/{nick}", "1", "JaviMarFer").with(csrf()))
		.andExpect(status().isUnauthorized());
	}

}

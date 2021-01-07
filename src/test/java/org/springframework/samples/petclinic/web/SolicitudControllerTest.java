package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class SolicitudControllerTest {

	@MockBean
	private SolicitudService solicitudService;
	
	@Autowired
	private MockMvc mockMvc; 
	
	@WithMockUser(value = "spring")
	@Test
	void testShowPendingRequest() throws Exception {
		given(this.solicitudService.getAllSolicitudes()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/requests/pending").sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotShowPendingRequest() throws Exception {
		mockMvc.perform(get("/requests/pending").sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShouldDenyPendingRequest() throws Exception {
		given(this.solicitudService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(put("/requests/decline/marrambla").with(csrf()).sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotDenyPendingRequest() throws Exception {
		mockMvc.perform(put("/requests/decline/marrambla").with(csrf()).sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShouldAcceptPendingRequest() throws Exception {
		given(this.solicitudService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(put("/requests/accept/marrambla").with(csrf()).sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotAcceptPendingRequest() throws Exception {
		given(this.solicitudService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(put("/requests/accept/marrambla").with(csrf()).sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
}

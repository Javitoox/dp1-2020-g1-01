package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class SolicitudControllerTests {

	@MockBean
	private SolicitudService solicitudService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Solicitud solicitud;
	
	@BeforeEach
	void setup() {
		solicitud = new Solicitud();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setFechaSolicitud(LocalDate.now());
		solicitud.setAlumno(alumno);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumSucces() throws Exception{
		//Gson gson = new Gson();
		//String jsonString = gson.toJson(solicitud);
		given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(null);
		
		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.param("alumno.nickUsuario", "JaviMartinez7")
				.param("alumno.contraseya", "JaviKuka787")
				.param("alumno.dniUsuario", "45676787Y")
				.param("alumno.nombreCompletoUsuario", "Javi Martinez")
				.param("alumno.correoElectronicoUsuario", "javikua7@gmail.com")
				.param("alumno.numTelefonoUsuario", "677676676")
				.param("alumno.direccionUsuario", "Calle Pepe")
				.param("alumno.fechaNacimiento", "2000-08-13"))
        .andExpect(status().isOk());
	}
	
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

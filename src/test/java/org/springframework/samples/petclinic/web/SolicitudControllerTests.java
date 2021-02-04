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
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class SolicitudControllerTests {

	@MockBean
	private SolicitudService solicitudService;
	@MockBean
	private AlumnoService alumnoService;
	@MockBean
	private TutorService tutorService;
	
	@Autowired
	private MockMvc mockMvc;
	private  Solicitud solicitud;
	private Solicitud solicitud2;
	@BeforeEach
	void setup() {
		solicitud = new Solicitud();
		solicitud2 = new Solicitud();
		Alumno alumno = new Alumno();
		Alumno alumno2 = new Alumno();
		Tutor tutor = new Tutor();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.of(2000, 10, 03));
		alumno2.setNickUsuario("JaviMartinez7");
		alumno2.setContraseya("JaviKuka787");
		alumno2.setDniUsuario("45676787Y");
		alumno2.setNombreCompletoUsuario("Javi Martinez");
		alumno2.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno2.setNumTelefonoUsuario("677676676");
		alumno2.setDireccionUsuario("Calle Pepe");
		alumno2.setFechaNacimiento(LocalDate.of(2012, 10, 03));
		tutor.setNickUsuario("Gonzalo");
		tutor.setContraseya("JaviKuka787");
		tutor.setDniUsuario("45676787G");
		tutor.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
		tutor.setCorreoElectronicoUsuario("gonzalo@gmail.com");
		tutor.setNumTelefonoUsuario("677673676");
		tutor.setDireccionUsuario("Calle Pepe");
		tutor.setFechaNacimiento(LocalDate.of(1990, 10, 03));
		solicitud.setAlumno(alumno);
		solicitud2.setTutor(tutor);
		solicitud2.setAlumno(alumno);
		
	}
	
	//Probando el sending
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testSendingNewAlumExtValidator() throws Exception{
//		given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(null);
//		mockMvc.perform(post("/requests/sending")
//				.with(csrf())
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(solicitud.toJson()))
//        .andExpect(status().isNonAuthoritativeInformation());
//	} 
//	
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumSucces() throws Exception{
		given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(null);
		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud.toJson()))
        .andExpect(status().isCreated());
	}
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumUpdatedGoodPass() throws Exception{
		given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(solicitud.getAlumno());
		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud.toJson()))
        .andExpect(status().isCreated());
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testSendingNewAlumError() throws Exception{
//		Solicitud a = new Solicitud();
//		Alumno doble = new Alumno();
//		doble.setNickUsuario("");
//		doble.setContraseya("");
//		doble.setDniUsuario("45676787das3Y");
//		doble.setNombreCompletoUsuario("Javi Martinez");
//		doble.setCorreoElectronicoUsuario("javikua7@gmail.com");
//		doble.setNumTelefonoUsuario("977676676");
//		doble.setDireccionUsuario("Calle Pepe");
//		doble.setFechaNacimiento(LocalDate.of(2000, 10, 03));
//		a.setAlumno(doble);
//		mockMvc.perform(post("/requests/sending")
//				.with(csrf())
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(a.toJson()))
//        .andExpect(status().isNonAuthoritativeInformation());
//	}
//	
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumUpdatedWrongPass() throws Exception{
		Alumno doble = new Alumno();
		doble.setNickUsuario("JaviMartinez7");
		doble.setContraseya("Espiderman88");
		doble.setDniUsuario("45676787Y");
		doble.setNombreCompletoUsuario("Javi Martinez");
		doble.setCorreoElectronicoUsuario("javikua7@gmail.com");
		doble.setNumTelefonoUsuario("677676676");
		doble.setDireccionUsuario("Calle Pepe");
		doble.setFechaNacimiento(LocalDate.of(2000, 10, 03));
		given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(doble);
		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud.toJson()))
        .andExpect(status().isOk());
	}
	
	//Empezamos con el sendingAll
	
	@WithMockUser(value = "spring")
	@Test
	void testSendingAllSucces() throws Exception{
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(null);
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(null);
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud2.toJson2()))
        .andExpect(status().isCreated());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testSendingAllUpdatedStudent() throws Exception{
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(solicitud2.getAlumno());
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(null);
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud2.toJson2()))
        .andExpect(status().isCreated());
	}
	@WithMockUser(value = "spring")
	@Test
	void testSendingAllUpdatedTutorCreatedStudent() throws Exception{
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(null);
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(solicitud2.getTutor());
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud2.toJson2()))
        .andExpect(status().isCreated());
	}
	@WithMockUser(value = "spring")
	@Test
	void testSendingAllUpdatedAll() throws Exception{
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(solicitud2.getAlumno());
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(solicitud2.getTutor());
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud2.toJson2()))
        .andExpect(status().isCreated());
	}
	
	//Estos dos no entran por algun motivo que desconozco
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumTutorUpdatedWrongPass() throws Exception{
		Alumno doble = new Alumno();
		doble.setNickUsuario("JaviMartinez7");
		doble.setContraseya("Espiderman88");
		doble.setDniUsuario("45676787Y");
		doble.setNombreCompletoUsuario("Javi Martinez");
		doble.setCorreoElectronicoUsuario("javikua7@gmail.com");
		doble.setNumTelefonoUsuario("677676676");
		doble.setDireccionUsuario("Calle Pepe");
		doble.setFechaNacimiento(LocalDate.of(2000, 10, 03));
		Tutor doble2 = new Tutor();
		doble2.setNickUsuario("Gonzalo");
		doble2.setContraseya("Espiderman88");
		doble2.setDniUsuario("45676787G");
		doble2.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
		doble2.setCorreoElectronicoUsuario("gonzalo@gmail.com");
		doble2.setNumTelefonoUsuario("677673676");
		doble2.setDireccionUsuario("Calle Pepe");
		doble2.setFechaNacimiento(LocalDate.of(1990, 10, 03));
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(doble);
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(doble2);
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud.toJson()))
        .andExpect(status().isOk());
	}
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumWrongPass() throws Exception{
		Alumno doble = new Alumno();
		doble.setNickUsuario("JaviMartinez7");
		doble.setContraseya("Espiderman88");
		doble.setDniUsuario("45676787Y");
		doble.setNombreCompletoUsuario("Javi Martinez");
		doble.setCorreoElectronicoUsuario("javikua7@gmail.com");
		doble.setNumTelefonoUsuario("677676676");
		doble.setDireccionUsuario("Calle Pepe");
		doble.setFechaNacimiento(LocalDate.of(2000, 10, 03));
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(doble);
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud.toJson()))
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
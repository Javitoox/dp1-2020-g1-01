package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class SolicitudControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SolicitudService solicitudService;
	
	@MockBean
	private AlumnoService alumnoService;
	
	@MockBean
	private TutorService tutorService;
	
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
		Gson gson = new Gson();
		String jsonString = gson.toJson(solicitud);
		log.info("INFORMAAAAAA: "+jsonString);
		given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(null);
		
		mockMvc.perform(post("/requests/sending")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(jsonString)
			    .with(csrf()))
		.andExpect(status().isOk());
	}

}

package org.springframework.samples.petclinic.web;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.samples.petclinic.service.FeedbackService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@WebMvcTest(controllers=FeedbackController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class FeedbackControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FeedbackService feedbackService;
	
	private static Alumno alumno;
	
//	public static String asJsonString(final Object obj) {
//	    try {
//	        final ObjectMapper mapper = new ObjectMapper();
//	        final String jsonContent = mapper.writeValueAsString(obj);
//	        System.out.println(jsonContent);
//	        return jsonContent;
//	    } catch (Exception e) {
//	        throw new RuntimeException(e);
//	    }
//	} 
//	
//	@BeforeEach
//	void data() {
//		alumno = new Alumno();
//		alumno.setNickUsuario("JaviMartinez7");
//		alumno.setContraseya("JaviKuka787");
//		alumno.setDniUsuario("45676787Y");
//		alumno.setNombreCompletoUsuario("Javi Martinez");
//		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
//		alumno.setNumTelefonoUsuario("677676676");
//		alumno.setDireccionUsuario("Calle Pepe");
//		//alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
//		//alumno.setFechaSolicitud(LocalDate.of(2020, 11, 2));
//	}
//	@WithMockUser(value = "spring")
//	@Test 
//	void testShouldStudentToMaterial() throws Exception {
//		Gson json = new Gson();
//		String a = json.toJson(alumno);
//		System.out.println(a); 
//		mockMvc.perform(put("/feedback/{idMaterial}/añadirAlumno",1)
//				.with(csrf())).andDo(print())
//		.andExpect(status().isOk());
//	  	
//	}
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldGetFeedback() throws Exception {
		mockMvc.perform(get("/feedback/obtenerFeedback/2")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());	
	}
	 
	@WithMockUser(value = "spring")
	@Test 
	void testShouldDeleteFeedback() throws Exception {
		mockMvc.perform(delete("/feedback/deleteMaterial/2")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());	
	}
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldChangeDone() throws Exception {
		mockMvc.perform(put("/feedback/cambiarDone/2")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());	
	}
	

	@WithMockUser(value = "spring")
	@Test 
	void testShouldUpdateFeedback() throws Exception {
		mockMvc.perform(put("/feedback/update")
				.param("comment", "nice")
				.param("rate", "5")
				.param("id", "3")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());	
	}
	
	@WithMockUser(username = "maribel", authorities = "alumno")
	@Test 
	void testShouldGetFeedbackStudent() throws Exception {
		mockMvc.perform(get("/feedback/maribel/2")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());	
	}
	
	@WithMockUser(username = "antonio", authorities = "alumno")
	@Test 
	void testShouldNotGetFeedbackStudent() throws Exception {
		mockMvc.perform(get("/feedback/maribel/2")
				.with(csrf())).andDo(print())
		.andExpect(status().isUnauthorized());	
	}
	
	
	
	
}

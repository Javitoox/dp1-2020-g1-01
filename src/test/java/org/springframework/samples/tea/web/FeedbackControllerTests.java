package org.springframework.samples.tea.web;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Feedback;
import org.springframework.samples.tea.service.FeedbackService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

@WebMvcTest(controllers= FeedbackController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class FeedbackControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FeedbackService feedbackService;

	@WithMockUser(value = "spring")
	@Test
	void testShoulAddStudentToMaterial() throws Exception {
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		Gson json = new Gson();
		mockMvc.perform(put("/feedback/2/anadirAlumno")
				.content(json.toJson(alumno)).contentType(MediaType.APPLICATION_JSON)
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());

	}

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
        Feedback f = new Feedback();
        f.setValoracion(2);
        f.setComentario("the best way to learn");
        f.setId(2);
	    given(feedbackService.getFeedbackByMaterialAndStudent(any(String.class), any(Integer.class))).willReturn(f);
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

    @WithMockUser(username = "maribel", authorities = "alumno")
    @Test
    void testShouldGetFeedbackStudentWithoutComment() throws Exception {
        Feedback f = new Feedback();
        f.setValoracion(2);
        f.setId(2);
        given(feedbackService.getFeedbackByMaterialAndStudent(any(String.class), any(Integer.class))).willReturn(f);
        mockMvc.perform(get("/feedback/maribel/2")
            .with(csrf())).andDo(print())
            .andExpect(status().isOk());
    }

    @WithMockUser(username = "maribel", authorities = "alumno")
    @Test
    void testShouldGetFeedbackStudentWithoutRate() throws Exception {
        Feedback f = new Feedback();
        f.setComentario("the best way to learn");
        f.setId(2);
        given(feedbackService.getFeedbackByMaterialAndStudent(any(String.class), any(Integer.class))).willReturn(f);
        mockMvc.perform(get("/feedback/maribel/2")
            .with(csrf())).andDo(print())
            .andExpect(status().isOk());
    }




}

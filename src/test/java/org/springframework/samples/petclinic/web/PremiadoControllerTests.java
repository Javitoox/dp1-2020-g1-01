package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PremiadoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;

@WebMvcTest(controllers=PremiadoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PremiadoControllerTests {

	@MockBean
	private PremiadoService premiadoService;
	
	@MockBean
	private AlumnoService alumnoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BindingResult bindingResult;
	
	@WithMockUser(value = "spring")
	@Test
	void testShowListPremiadosPorFechaLoggedAsProfesor() throws Exception {
		given(this.premiadoService.premiadosPorFecha(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/premiados/2020-W50").sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowListPremiadosPorFechaLoggedAsStudent() throws Exception {
		given(this.premiadoService.premiadosPorFecha(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/premiados/2020-W50").sessionAttr("type","alumno")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowListPremiadosPorFechaLoggedAsTutor() throws Exception {
		given(this.premiadoService.premiadosPorFecha(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/premiados/2020-W50").sessionAttr("type","tutor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotShowListPremiadosPorFechaNotLogged() throws Exception {
		given(this.premiadoService.premiadosPorFecha(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/premiados/2020-W50")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShouldDeletePremiado() throws Exception {
		mockMvc.perform(delete("/premiados/borrarPremiado/1").with(csrf()).sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShoulNotdDeletePremiado() throws Exception {
		mockMvc.perform(delete("/premiados/borrarPremiado/1").with(csrf()).sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	/*
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testShouldNotCreatePremiadoBecauseOfErrors() throws Exception {
	 * Mockito.when(bindingResult.hasErrors()).thenReturn(true); Path
	 * directorioImagenes
	 * =Paths.get("src//main//resources//static//frontend//public/photosWall");
	 * String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath(); Path
	 * rutaCompleta = Paths.get(rutaAbsoluta + "//Javi.jpg"); MultipartFile file =
	 * new MockMultipartFile("file.jpg", Files.readAllBytes(rutaCompleta));
	 * 
	 * mockMvc.perform(post("/premiados/añadirPremiado/2020-W50")
	 * .param("description", "The best") .param("nickUsuario", "marrambla3")
	 * .param("photo", file).with(csrf())
	 * .sessionAttr("type","profesor")).andExpect(status().
	 * isNonAuthoritativeInformation());
	 * 
	 * }
	 */
	
	
	
	
	
	
	
	
}

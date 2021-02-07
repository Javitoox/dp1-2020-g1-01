package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.BodyPremiado;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PremiadoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.samples.petclinic.configuration.SecurityConfiguration;

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
	
	private static BodyPremiado BODY;
	
	@BeforeEach
	void data() throws IOException {
		Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/photosWall");
		  String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath(); 
		  Path rutaCompleta = Paths.get(rutaAbsoluta + "//Javi.jpg"); 
		  MultipartFile file = new MockMultipartFile("file.jpg", Files.readAllBytes(rutaCompleta));
		  
		  BODY= new BodyPremiado();
		  BODY.setDescription("The best");
		  BODY.setNickUsuario("marrambla3");
		  BODY.setPhoto(file);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowListPremiadosPorFechaLogged() throws Exception {
		given(this.premiadoService.premiadosPorFecha(any(String.class))).willReturn(new ArrayList<>());
		mockMvc.perform(get("/premiados/2020-W50")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShouldDeletePremiado() throws Exception {
		mockMvc.perform(delete("/premiados/borrarPremiado/1").with(csrf())).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldCreatePremiado() throws Exception {
		  given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		  given(this.premiadoService.numAparicionesEnFecha(any(String.class), any(String.class))).willReturn(0); 
		  mockMvc.perform(multipart("/premiados/anadirPremiado/2020-W50")
				  .file("photo", BODY.getPhoto().getBytes())
				  .param("description", BODY.getDescription())
				  .param("nickUsuario", BODY.getNickUsuario())
				  .with(csrf())
				  .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
		  .andExpect(status().isCreated());
	  
	  }
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldNotCreatePremiadoBecauseStudentAppearInTheWeek() throws Exception {
		  given(this.alumnoService.getAlumno(any(String.class))).willReturn(new Alumno());
		  given(this.premiadoService.numAparicionesEnFecha(any(String.class), any(String.class))).willReturn(1);  
		  mockMvc.perform(multipart("/premiados/anadirPremiado/2020-W50")
				  .file("photo", BODY.getPhoto().getBytes())
				  .param("description", BODY.getDescription())
				  .param("nickUsuario", BODY.getNickUsuario())
				  .with(csrf())
				  .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
		  .andExpect(status().isAlreadyReported());
	  
	  } 
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldNotCreatePremiadoBecauseStudentDoesntExist() throws Exception {
		  given(this.alumnoService.getAlumno(any(String.class))).willReturn(null);  
		  mockMvc.perform(multipart("/premiados/anadirPremiado/2020-W50")
				  .file("photo", BODY.getPhoto().getBytes())
				  .param("description", BODY.getDescription())
				  .param("nickUsuario", BODY.getNickUsuario())
				  .with(csrf())
				  .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
		  .andExpect(status().isNoContent());
	  
	  }
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldNotCreatePremiadoBecauseTheRequestHasErrors() throws Exception {		     
		  mockMvc.perform(multipart("/premiados/anadirPremiado/2020-W50")
				  .param("description", BODY.getDescription())
				  .with(csrf())
				  .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)).andDo(print())
		  .andExpect(status().isNonAuthoritativeInformation());
	  
	  } 
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldEditPremiado() throws Exception {		     
		  mockMvc.perform(put("/premiados/editarPremiado")
				  .param("description", BODY.getDescription())
				  .param("id", "2")
				  .param("nickUsuario", "marrambla2")
				  .with(csrf())
				  .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
		  .andDo(print())
		  .andExpect(status().isCreated());
	  
	  } 
	
	@WithMockUser(value = "spring")
	@Test 
	void testShouldGetLastWeek() throws Exception {		     
		  mockMvc.perform(get("/premiados/ultimaSemana")
				  .with(csrf()))
		  .andDo(print())
		  .andExpect(status().isOk());
	  
	  } 

	
}
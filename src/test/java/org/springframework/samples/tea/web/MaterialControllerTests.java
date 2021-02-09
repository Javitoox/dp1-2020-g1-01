package org.springframework.samples.tea.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.BodyMaterial;
import org.springframework.samples.tea.model.Material;
import org.springframework.samples.tea.service.MaterialService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;


@WebMvcTest(controllers= MaterialController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MaterialControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MaterialService materialService;

	@MockBean
	private BindingResult bindingResult;

	@Test
	@WithMockUser(username = "maribel", authorities = "profesor")
	void testShouldGetMaterialByProfesor() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByProfesor/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "antonio", authorities = "profesor")
	void testShouldNotGetMaterialByProfesor() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByProfesor/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "maribel", authorities = "alumno")
	void testShouldGetMaterialByStudent() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByAlumno/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "antonio", authorities = "alumno")
	void testShouldNotGetMaterialByStudent() throws Exception {
		mockMvc.perform(get("/materiales/getMaterialByAlumno/maribel")
				.with(csrf())).andDo(print())
		.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "maribel", authorities = "profesor")
	void testShouldUploadMaterial() throws Exception {
		given(this.materialService.uploadMaterial(any(MultipartFile.class), any(String.class), any(String.class))).willReturn(new Material());

		Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		Path rutaCompleta = Paths.get(rutaAbsoluta + "//1.pdf");
		MultipartFile file = new MockMultipartFile("test.pdf", Files.readAllBytes(rutaCompleta));
		BodyMaterial body= new BodyMaterial();
		body.setTipoMaterial("Homework");
		body.setPdf(file);

		mockMvc.perform(multipart("/materiales/anadirMaterial/maribel")
				.file("pdf", body.getPdf().getBytes())
				.param("tipoMaterial", body.getTipoMaterial())
				.with(csrf())).andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "antonio", authorities = "profesor")
	void testShouldNotUploadMaterialBecauseNoCorrectUser() throws Exception {
		Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		Path rutaCompleta = Paths.get(rutaAbsoluta + "//1.pdf");
		MultipartFile file = new MockMultipartFile("test.pdf", Files.readAllBytes(rutaCompleta));
		BodyMaterial body= new BodyMaterial();
		body.setTipoMaterial("Homework");
		body.setPdf(file);

		mockMvc.perform(multipart("/materiales/anadirMaterial/maribel")
				.file("pdf", body.getPdf().getBytes())
				.param("tipoMaterial", body.getTipoMaterial())
				.with(csrf())).andDo(print())
		.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "maribel", authorities = "profesor")
	void testShouldNotUploadMaterialBecauseParamErrors() throws Exception {
		Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		Path rutaCompleta = Paths.get(rutaAbsoluta + "//1.pdf");
		MultipartFile file = new MockMultipartFile("test.pdf", Files.readAllBytes(rutaCompleta));
		BodyMaterial body= new BodyMaterial();
		body.setTipoMaterial("Homework");
		body.setPdf(file);

		mockMvc.perform(multipart("/materiales/anadirMaterial/maribel")
				.file("pdf", body.getPdf().getBytes())
				.with(csrf())).andDo(print())
		.andExpect(status().isNonAuthoritativeInformation());
	}

}

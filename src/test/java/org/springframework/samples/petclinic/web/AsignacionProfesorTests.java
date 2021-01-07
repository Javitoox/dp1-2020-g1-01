package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AsignacionProfesorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = AsignacionesProfesorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AsignacionProfesorTests {
	
	private final static String NICK_USUARIO = "Evelyn";
	
	@MockBean
	private AsignacionProfesorService asignacionProfesorService;
	
	private AsignacionProfesor asignacionProfesor;
	
	@Autowired
    private MockMvc mockMvc;
	
	
	@BeforeEach
	void setup() {
		asignacionProfesor = new AsignacionProfesor();
		Grupo grupo = new Grupo();
		Curso curso = new Curso();
		AsignacionProfesorKey asignacionProfesorKey = new AsignacionProfesorKey();
		asignacionProfesorKey.setNickProfesor(NICK_USUARIO);
		asignacionProfesorKey.setNombreGrupo("grupo7");
		curso.setCursoDeIngles(TipoCurso.B2);
		grupo.setNombreGrupo("grupo7");
		grupo.setCursos(curso);
		Profesor profesor = new Profesor();
		profesor.setNickUsuario("JaviMartinez7");
		profesor.setContraseya("JaviKuka787");
		profesor.setDniUsuario("45676787Y");
		profesor.setNombreCompletoUsuario("Javi Martinez");
		profesor.setCorreoElectronicoUsuario("javikua7@gmail.com");
		profesor.setNumTelefonoUsuario("677676676");
		profesor.setDireccionUsuario("Calle Pepe");
		profesor.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		asignacionProfesor.setProfesor(profesor);
		asignacionProfesor.setFecha(LocalDate.now());
		asignacionProfesor.setGrupo(grupo);
		asignacionProfesor.setId(asignacionProfesorKey);
	}
	@WithMockUser(value = "spring")
	@Test
	void testShowAssignationListByTeacher() throws Exception {
		given(this.asignacionProfesorService.getAllAsignacionesByUser(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/asignaciones/{user}", NICK_USUARIO).sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDontShowAssignationListByTeacherIfLoggedAsAlumn() throws Exception {
		given(this.asignacionProfesorService.getAllAsignacionesByUser(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/asignaciones/{user}", NICK_USUARIO).sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowAListGroupIfNotLogged() throws Exception{
		mockMvc.perform(get("/asignaciones/{user}", NICK_USUARIO).sessionAttr("type","null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAllAssignationLists() throws Exception {
		given(this.asignacionProfesorService.getFreeGroups()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/asignaciones/freeAssignments").sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDontShowAllAssignationListsIfLoggedAsAlumn() throws Exception {
		given(this.asignacionProfesorService.getFreeGroups()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/asignaciones/freeAssignments").sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowAllAssignationListsIfNotLogged() throws Exception{
		given(this.asignacionProfesorService.getFreeGroups()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/asignaciones/freeAssignments").sessionAttr("type","null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAssignmentucces() throws Exception{
		Gson gson = new Gson();
		String jsonString = gson.toJson(asignacionProfesor);
		log.info("Informa: "+jsonString);
		
		mockMvc.perform(post("/asignaciones/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(jsonString)
			    .with(csrf()))
		.andExpect(status().isOk());
	}
	
	
	
	/*Quedan los del post*/
}

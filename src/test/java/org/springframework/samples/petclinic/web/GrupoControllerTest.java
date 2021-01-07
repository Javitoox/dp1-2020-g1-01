package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = GrupoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class GrupoControllerTest {
	
	private final static String GROUP_NAME = "Grupo de Evelyn";
	private final static TipoCurso CURSO = TipoCurso.A1;
	private static Set<Grupo> listaGrupos;
	private static List<String> nombresGrupos;
	private static List<Alumno> alumnos;
	private static List<Alumno> alumnosLleno;

	
	private static Grupo g;
	private static Curso c;
	@MockBean
	private GrupoService grupoService;
	@MockBean
	private AlumnoService alumnoService;
	
	@Autowired
    private MockMvc mockMvc;
	
	
	@BeforeEach
	void setup() {
		listaGrupos = new HashSet<>();
		c = new Curso();
		c.setCursoDeIngles(CURSO);
		
 		g = new Grupo();
		g.setCursos(c);
		g.setNombreGrupo(GROUP_NAME);
		
		alumnos = new ArrayList<>();
		alumnosLleno = new ArrayList<>();
		Alumno a = new Alumno();
		alumnosLleno.add(a);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowAListGroupIfLoggedAsTeacher() throws Exception{
		given(this.grupoService.getAllGrupos()).willReturn(listaGrupos);
		mockMvc.perform(get("/grupos/all").sessionAttr("type", "profesor")).andExpect(status().isOk());
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowAListGroupIfLoggedAsAlumn() throws Exception{
		mockMvc.perform(get("/grupos/all").sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
		
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowAListGroupIfNotLogged() throws Exception{
		mockMvc.perform(get("/grupos/all").sessionAttr("type", "null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowANameGroupListByCourseIfLoggedAsTeacher() throws Exception{
		given(this.grupoService.getNameGruposByCourse(CURSO)).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/nombresGrupo/{curso}", CURSO).sessionAttr("type", "profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowANameGroupListByCourseIfLoggedAsAlumn() throws Exception{
		mockMvc.perform(get("/grupos/nombresGrupo/{curso}", CURSO).sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowANamesCoursesListByGroupIfLoggedAsTeacher() throws Exception{
		given(this.grupoService.getCursoByGrupo(GROUP_NAME)).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/nombreCurso/{grupo}", GROUP_NAME).sessionAttr("type", "profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowANamesCoursesListByGroupIfLoggedAsAlumn() throws Exception{
		mockMvc.perform(get("/grupos/nombreCurso/{grupo}", GROUP_NAME).sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowNamesCoursesListByGroupIfNotLogged() throws Exception{
		mockMvc.perform(get("/grupos/nombreCurso/{grupo}", GROUP_NAME).sessionAttr("type", "null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowNamesGroupsListIfLoggedAsTeacher() throws Exception{
		given(this.grupoService.getGroupNames()).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/allGroupNames").sessionAttr("type", "profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowNamesGroupsListIfLoggedAsAlumn() throws Exception{
		mockMvc.perform(get("/grupos/allGroupNames").sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowNamesGroupsListIfNotLogged() throws Exception{
		mockMvc.perform(get("/grupos/allGroupNames").sessionAttr("type", "null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowEmptyNamesGroupsListIfLoggedAsTeacher() throws Exception{
		given(this.grupoService.getEmptyGroups()).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/allEmptyGroups").sessionAttr("type", "profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowEmptyNamesGroupsListIfLoggedAsAlumn() throws Exception{
		mockMvc.perform(get("/grupos/allEmptyGroups").sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowEmptyNamesGroupsListIfNotLogged() throws Exception{
		mockMvc.perform(get("/grupos/allEmptyGroups").sessionAttr("type", "null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDeleteGroupAsTeacher() throws Exception {
		given(this.alumnoService.getStudentsPerGroup(GROUP_NAME)).willReturn(alumnos);
		mockMvc.perform(delete("/grupos/delete/{nombreGrupo}", GROUP_NAME).with(csrf()).sessionAttr("type", "profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDeleteGroupWithAlumnsAsTeacher() throws Exception {
		given(this.alumnoService.getStudentsPerGroup(GROUP_NAME)).willReturn(alumnosLleno);
		mockMvc.perform(delete("/grupos/delete/{nombreGrupo}", GROUP_NAME).with(csrf()).sessionAttr("type", "profesor")).andExpect(status().isBadRequest());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDeleteGroupAsAlumn() throws Exception {
		mockMvc.perform(delete("/grupos/delete/{nombreGrupo}", GROUP_NAME).with(csrf()).sessionAttr("type", "alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDeleteGroupIfNotLogged() throws Exception {
		mockMvc.perform(delete("/grupos/delete/{nombreGrupo}", GROUP_NAME).with(csrf()).sessionAttr("type", "null")).andExpect(status().isUnauthorized());
	}
	
	/*Quedan los del post*/
}

package org.springframework.samples.tea.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.service.AlumnoService;
import org.springframework.samples.tea.service.GrupoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

@WebMvcTest(controllers = GrupoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GrupoControllerTests {

	private final static String GROUP_NAME = "Grupo de Evelyn";
	private final static String CURSO = "A1";
	private static Set<Grupo> listaGrupos;
	private static List<String> nombresGrupos;


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
		g = new Grupo();
		g.setCursos(c);
		g.setNombreGrupo(GROUP_NAME);
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllGroups() throws Exception {
		given(this.grupoService.getAllGrupos()).willReturn(listaGrupos);
		mockMvc.perform(get("/grupos/all")).andExpect(status().isOk());

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowANameGroupListByCourse() throws Exception {
		given(this.grupoService.getNameGruposByCourse(CURSO)).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/nombresGrupo/{curso}", CURSO)).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowANamesCoursesListByGroup() throws Exception {
		given(this.grupoService.getCursoByGrupo(GROUP_NAME)).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/nombreCurso/{grupo}", GROUP_NAME)).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAListWithAllGroupsNames() throws Exception {
		given(this.grupoService.getGroupNames()).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/allGroupNames")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllAsignableGroupsNames() throws Exception {
		given(this.grupoService.getAssignableGroupsByStudent("JaviKuka")).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos//allAsignableGroups/{nickUsuario}", "JaviKuka")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowEmptyNamesGroupsList() throws Exception {
		given(this.grupoService.getEmptyGroups()).willReturn(nombresGrupos);
		mockMvc.perform(get("/grupos/allEmptyGroups")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteGroup() throws Exception {
		given(this.grupoService.grupoVacio(GROUP_NAME)).willReturn(true);
		mockMvc.perform(delete("/grupos/delete/{nombreGrupo}", GROUP_NAME).with(csrf())).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteGroupWithAlumnsAsTeacher() throws Exception {
		given(this.grupoService.grupoVacio(GROUP_NAME)).willReturn(false);
		mockMvc.perform(delete("/grupos/delete/{nombreGrupo}", GROUP_NAME).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreatingNewGroup() throws Exception {
		Gson gson = new Gson();
		String jsonString = gson.toJson(g);

		mockMvc.perform(post("/grupos/new").contentType(MediaType.APPLICATION_JSON).content(jsonString).with(csrf()))
				.andExpect(status().isCreated());
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreatingExistentGroup() throws Exception {
		Gson gson = new Gson();
		String jsonString = gson.toJson(g);
		given(this.grupoService.exists(GROUP_NAME)).willReturn(true);
		mockMvc.perform(post("/grupos/new").contentType(MediaType.APPLICATION_JSON).content(jsonString).with(csrf()))
				.andExpect(status().isImUsed());
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreatingExistentGroupIfRequestHasErrors() throws Exception {
		Curso c = new Curso();
		Grupo g = new Grupo();
		g.setCursos(c); /*
						 * Para que nos salte el error no introducimos el id del grupo, en este caso el
						 * nombre
						 */
		Gson gson = new Gson();
		String jsonString = gson.toJson(g);
		mockMvc.perform(post("/grupos/new").contentType(MediaType.APPLICATION_JSON).content(jsonString).with(csrf()))
				.andExpect(status().isNonAuthoritativeInformation());

	}

}

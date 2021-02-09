package org.springframework.samples.tea.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.AsignacionProfesor;
import org.springframework.samples.tea.model.AsignacionProfesorKey;
import org.springframework.samples.tea.model.Curso;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.model.Profesor;
import org.springframework.samples.tea.service.AsignacionProfesorService;
import org.springframework.samples.tea.service.ProfesorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AsignacionesProfesorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AsignacionProfesorControllerTests {

	private final static String NICK_USUARIO = "Evelyn";

	@MockBean
	private AsignacionProfesorService asignacionProfesorService;

	@MockBean
	private ProfesorService profesorService;

	private AsignacionProfesor asignacionProfesor;
	private Profesor profesor;
	private Grupo grupo;

	@Autowired
    private MockMvc mockMvc;


	@BeforeEach
	void setup() {
		asignacionProfesor = new AsignacionProfesor();
		AsignacionProfesorKey asignacionProfesorKey = new AsignacionProfesorKey();
		asignacionProfesorKey.setNickProfesor(NICK_USUARIO);
		asignacionProfesorKey.setNombreGrupo("grupo7");
		grupo = new Grupo();
		Curso curso = new Curso();
		grupo.setNombreGrupo("grupo7");
		grupo.setCursos(curso);
		profesor = new Profesor();
		profesor.setNickUsuario(NICK_USUARIO);
		asignacionProfesor.setFecha(LocalDate.of(2020, 11, 11));
		asignacionProfesor.setGrupo(grupo);
		asignacionProfesor.setId(asignacionProfesorKey);

	}
	@WithMockUser(value = NICK_USUARIO, authorities = {"profesor"})
	@Test
	void testShowAssignationListByTeacher() throws Exception {
		List<AsignacionProfesor> asignaciones = new ArrayList<>();
		asignaciones.add(asignacionProfesor);
		given(this.asignacionProfesorService.getAllAsignacionesByUser(NICK_USUARIO)).willReturn(asignaciones);
		mockMvc.perform(get("/asignaciones/get/{user}", NICK_USUARIO)).andExpect(status().isOk());
	}

	@WithMockUser(value = NICK_USUARIO, authorities = {"profesor"})
	@Test
	void testShowAssignationListByTeacherIsUnauthorized() throws Exception {
		List<AsignacionProfesor> asignaciones = new ArrayList<>();
		asignaciones.add(asignacionProfesor);
		given(this.asignacionProfesorService.getAllAsignacionesByUser(NICK_USUARIO)).willReturn(asignaciones);
		mockMvc.perform(get("/asignaciones/get/{user}", "JavierM")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowTeachersNameByGroup() throws Exception {
		List<String> profesores = new ArrayList<>();
		profesores.add(profesor.getNickUsuario());
		given(this.asignacionProfesorService.findAsignacionesByGroup(grupo.getNombreGrupo())).willReturn(profesores);
		mockMvc.perform(get("/asignaciones/getNick/{nombreGrupo}", grupo.getNombreGrupo())).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAssignmentSucces() throws Exception{
		mockMvc.perform(post("/asignaciones/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(asignacionProfesor.toJson())
			    .with(csrf()))
		.andExpect(status().isCreated());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAssignmentIsNotOk() throws Exception{
		Grupo g = new Grupo();
		g.setNombreGrupo("");
		asignacionProfesor.setGrupo(g);;
		profesor.setNickUsuario("Maria");
		mockMvc.perform(post("/asignaciones/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(asignacionProfesor.toJson())
			    .with(csrf()))
		.andExpect(status().isImUsed());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAssignmentIsNotOK2() throws Exception{
		Grupo g = new Grupo();
		g.setNombreGrupo(null);
		asignacionProfesor.setGrupo(g);;
		profesor.setNickUsuario("Maria");
		mockMvc.perform(post("/asignaciones/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(asignacionProfesor.toJson())
			    .with(csrf()))
		.andExpect(status().isImUsed());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAssignmentIsNonAuthoritative() throws Exception{
		AsignacionProfesorKey id2 = new AsignacionProfesorKey();
		id2.setNickProfesor("");
		id2.setNombreGrupo("");
		asignacionProfesor.setId(id2);
		mockMvc.perform(post("/asignaciones/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(asignacionProfesor.toJson())
			    .with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation());
	}

	@WithMockUser(value = NICK_USUARIO, authorities = {"profesor"})
	@Test
	void testDeletingAssignationIsOk() throws Exception{
		mockMvc.perform(delete("/asignaciones/delete/{nickProfesor}/{nombreGrupo}", NICK_USUARIO, grupo.getNombreGrupo())
				.with(csrf())).andExpect(status().isOk());
	}

	@WithMockUser(value = NICK_USUARIO, authorities = {"profesor"})
	@Test
	void testDeletingAssignationIsUnauthorized() throws Exception{
		mockMvc.perform(delete("/asignaciones/delete/{nickProfesor}/{nombreGrupo}", "Paco", grupo.getNombreGrupo())
				.with(csrf())).andExpect(status().isUnauthorized());
	}
}

package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Inscripcion;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.service.EventoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=EventoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EventoControllerTests {

	@MockBean
	private EventoService eventoService;

	@Autowired
	private MockMvc mockMvc;

	private Evento evento;

	private Alumno alumno;

	private Inscripcion inscripcion;

	@BeforeAll
	void setup() {
		inscripcion = new Inscripcion();
		inscripcion.setFecha(LocalDate.now());
		inscripcion.setRegistrado(true);

		evento = new Evento();
		TipoEvento tipo = new TipoEvento();
		evento.setTitle("El evento de prueba");
		evento.setDescripcion("Que descripcion mas bonita");
		evento.setTipo(tipo);
		evento.setColor("red");
		evento.setStart(LocalDate.of(2021, 01, 20));
		evento.setEnd(LocalDate.of(2021, 01, 22));

		Curso curso = new Curso();
		alumno = new Alumno();
 		Grupo grupo = new Grupo();
		grupo.setCursos(curso);
		grupo.setNombreGrupo("Grupo C4");
		alumno.setGrupos(grupo);
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setFechaSolicitud(LocalDate.now());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllEvents() throws Exception {
		given(eventoService.getAll()).willReturn(new ArrayList<>());

		mockMvc.perform(get("/events/all"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@WithMockUser(username = "JaviMartinez7", authorities = {"alumno"})
	@Test
	void testEventsByCourse() throws Exception {
		List<Evento> eventos = new ArrayList<>();
		eventos.add(evento);
		given(eventoService.getAlumEvents(any())).willReturn(eventos);

		mockMvc.perform(get("/events/getByCourse/{nick}",alumno.getNickUsuario()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@WithMockUser(username = "JaviM", authorities = {"alumno"})
	@Test
	void testEventsByCourseNotAuth() throws Exception {
		mockMvc.perform(get("/events/getByCourse/{nick}",alumno.getNickUsuario()))
		.andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateEvent() throws Exception {
		given(eventoService.updateDateEvent(any(), any(), any())).willReturn(evento);

		mockMvc.perform(put("/events/update/1/Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)/Thu Jan 09 2021 00:00:00 GMT+0100 (hora estándar de Europa central)")
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateEventNull() throws Exception {
		given(eventoService.updateDateEvent(any(), any(), any())).willReturn(null);

		mockMvc.perform(put("/events/update/1/Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)/Thu Jan 09 2021 00:00:00 GMT+0100 (hora estándar de Europa central)")
				.with(csrf()))
		.andExpect(status().isNotFound());
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetDescription() throws Exception {
		given(eventoService.getDescription(1)).willReturn("Description");

		mockMvc.perform(get("/events/description/{id}",1))
		.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetDescriptionNull() throws Exception {
		given(eventoService.getDescription(1)).willReturn(null);

		mockMvc.perform(get("/events/description/{id}",1))
		.andExpect(status().isNotFound());
	}

	@WithMockUser(username = "JaviMartinez7", authorities = {"alumno"})
	@Test
	void testGetStudentDescription() throws Exception {
		given(eventoService.getDescriptionAlumno(1, alumno.getNickUsuario())).willReturn("Description");

		mockMvc.perform(get("/events/descriptionAlumno/{id}/{nickUser}", 1, alumno.getNickUsuario()))
		.andExpect(status().isOk());
	}

	@WithMockUser(username = "JaviMartinez7", authorities = {"alumno"})
	@Test
	void testGetStudentDescriptionNull() throws Exception {
		given(eventoService.getDescriptionAlumno(1, alumno.getNickUsuario())).willReturn(null);

		mockMvc.perform(get("/events/descriptionAlumno/{id}/{nickUser}", 1, alumno.getNickUsuario()))
		.andExpect(status().isNotFound());
	}

	@WithMockUser(username = "JaviM", authorities = {"alumno"})
	@Test
	void testGetStudentDescriptionNotAuth() throws Exception {
		mockMvc.perform(get("/events/descriptionAlumno/{id}/{nickUser}", 1, alumno.getNickUsuario()))
		.andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteEvent() throws Exception {
		mockMvc.perform(delete("/events/delete/{id}",1).with(csrf()))
		.andExpect(status().isOk());

		verify(eventoService, times(1)).deleteDescription(any());
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateEvent() throws Exception {
		given(eventoService.existEvent(any())).willReturn(false);
		given(eventoService.assignEvent(any(), any(), any())).willReturn(true);

		mockMvc.perform(post("/events/create/{curso}", "A1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento.toJson())
				.with(csrf()))
		.andExpect(status().isCreated());
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateEventWithErrorsDate() throws Exception {
		Evento evento2 = new Evento();
		TipoEvento tipo2 = new TipoEvento();
		evento2.setTitle("El evento de prueba");
		evento2.setDescripcion("Que descripcion mas bonita");
		evento2.setTipo(tipo2);
		evento2.setColor("red");
		evento2.setStart(LocalDate.of(2021, 01, 20));
		evento2.setEnd(LocalDate.of(2021, 01, 19));

		mockMvc.perform(post("/events/create/{curso}", "A1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento2.toJson())
				.with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation())
		.andExpect(jsonPath("$[0].field", is("start")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateEventWithErrorsNullTitle() throws Exception {
		Evento evento2 = new Evento();
		TipoEvento tipo2 = new TipoEvento();
		evento2.setDescripcion("Que descripcion mas bonita");
		evento2.setTipo(tipo2);
		evento2.setColor("red");
		evento2.setStart(LocalDate.of(2021, 01, 20));
		evento2.setEnd(LocalDate.of(2021, 01, 21));

		mockMvc.perform(post("/events/create/{curso}", "A1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento2.toJson())
				.with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation())
		.andExpect(jsonPath("$[0].field", is("title")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateEventWithErrorsNullCurso() throws Exception {
		Evento evento2 = new Evento();
		TipoEvento tipo2 = new TipoEvento();
		evento2.setTitle("El evento de prueba");
		evento2.setDescripcion("Que descripcion mas bonita");
		evento2.setTipo(tipo2);
		evento2.setColor("red");
		evento2.setStart(LocalDate.of(2021, 01, 20));
		evento2.setEnd(LocalDate.of(2021, 01, 21));

		mockMvc.perform(post("/events/create/{curso}", "null")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento2.toJson())
				.with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation())
		.andExpect(jsonPath("$[0].field", is("curso")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateEventWithErrorsExistEvent() throws Exception {
		given(eventoService.existEvent(any())).willReturn(true);

		mockMvc.perform(post("/events/create/{curso}", "A1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento.toJson())
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("The event already exists")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateEventWithErrorsExistType() throws Exception {
		given(eventoService.existEvent(any())).willReturn(false);
		given(eventoService.assignEvent(any(), any(), any())).willReturn(false);

		mockMvc.perform(post("/events/create/{curso}", "A1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento.toJson())
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("Type or course not exist")));
	}

}

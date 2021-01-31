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
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.service.AlumnoService;
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
	
	@MockBean
	private AlumnoService alumService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Evento evento;
	
	private Alumno alumno;
	
	@BeforeAll
	void setup() {
		evento = new Evento();
		TipoEvento tipo = new TipoEvento();
		//tipo.setTipo("internal");
		evento.setTitle("El evento de prueba");
		evento.setDescripcion("Que descripcion mas bonita");
		evento.setTipo(tipo);
		evento.setColor("red");
		evento.setStart(LocalDate.of(2021, 01, 20));
		evento.setEnd(LocalDate.of(2021, 01, 22));
		
		Curso curso = new Curso();
		curso.setCursoDeIngles(TipoCurso.A1);
		alumno = new Alumno();
 		Grupo grupo = new Grupo();
		grupo.setCursos(curso);
		grupo.setNombreGrupo("Grupo C4");
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setFechaSolicitud(LocalDate.now());
		alumno.setGrupos(grupo);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAllEvents() throws Exception {
		given(eventoService.getAll()).willReturn(new ArrayList<>());
		
		mockMvc.perform(get("/events/all").sessionAttr("type","profesor"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAllEventsNotAuth() throws Exception {
		given(eventoService.getAll()).willReturn(new ArrayList<>());
		
		mockMvc.perform(get("/events/all").sessionAttr("type","usuario"))
		.andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testEventsByCourse() throws Exception {
		List<Evento> eventos = new ArrayList<>();
		eventos.add(evento);
		given(alumService.getAlumno(alumno.getNickUsuario())).willReturn(alumno);
		given(eventoService.getByCourse(alumno.getGrupos().getCursos())).willReturn(eventos);
		
		mockMvc.perform(get("/events/getByCourse/{nick}",alumno.getNickUsuario()).sessionAttr("type","alumno"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testEventsByCourseNotAuth() throws Exception {
		given(alumService.getAlumno(any())).willReturn(null);
		given(eventoService.getByCourse(any())).willReturn(null);
		
		mockMvc.perform(get("/events/getByCourse/{nick}",alumno.getNickUsuario()).sessionAttr("type","usuario"))
		.andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testUpdateEvent() throws Exception {
		given(eventoService.updateDateEvent(any(), any(), any())).willReturn(evento);
		
		mockMvc.perform(put("/events/update/1/Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)/Thu Jan 09 2021 00:00:00 GMT+0100 (hora estándar de Europa central)")
				.with(csrf()).sessionAttr("type","profesor"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testUpdateEventNull() throws Exception {
		given(eventoService.updateDateEvent(any(), any(), any())).willReturn(null);
		
		mockMvc.perform(put("/events/update/1/Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)/Thu Jan 09 2021 00:00:00 GMT+0100 (hora estándar de Europa central)")
				.with(csrf()).sessionAttr("type","profesor"))
		.andExpect(status().isNotFound());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testUpdateEventNotAuth() throws Exception {
		given(eventoService.updateDateEvent(any(), any(), any())).willReturn(evento);
		
		mockMvc.perform(put("/events/update/1/Thu Jan 07 2021 00:00:00 GMT+0100 (hora estándar de Europa central)/Thu Jan 09 2021 00:00:00 GMT+0100 (hora estándar de Europa central)")
				.with(csrf()).sessionAttr("type","usuario"))
		.andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testGetDescription() throws Exception {
		given(eventoService.getDescription(1)).willReturn("Description");
		
		mockMvc.perform(get("/events/description/{id}",1).sessionAttr("type","alumno"))
		.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testGetDescriptionNull() throws Exception {
		given(eventoService.getDescription(1)).willReturn(null);
		
		mockMvc.perform(get("/events/description/{id}",1).sessionAttr("type","alumno"))
		.andExpect(status().isNotFound());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testGetDescriptionNotAuth() throws Exception {
		given(eventoService.getDescription(1)).willReturn("amazing");
		
		mockMvc.perform(get("/events/description/{id}",1).sessionAttr("type","usuario"))
		.andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteEvent() throws Exception {
		mockMvc.perform(delete("/events/delete/{id}",1).with(csrf()).sessionAttr("type","profesor"))
		.andExpect(status().isOk());
		
		verify(eventoService, times(1)).deleteDescription(any());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteEventNotAuth() throws Exception {
		mockMvc.perform(delete("/events/delete/{id}",1).with(csrf()).sessionAttr("type","alumno"))
		.andExpect(status().isUnauthorized());
		
		verify(eventoService, times(0)).deleteDescription(any());
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testCreateEvent() throws Exception {
//		given(eventoService.existEvent(any())).willReturn(false);
//		given(eventoService.assignTypeAndSave(any(), any())).willReturn(true);
//		
//		mockMvc.perform(post("/events/create/{curso}", "A1")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(evento.toJson())
//				.with(csrf()).sessionAttr("type","profesor"))
//		.andExpect(status().isCreated());
//	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testCreateEventWithErrorsDate() throws Exception {
//		Evento evento2 = new Evento();
//		TipoEvento tipo2 = new TipoEvento();
//		tipo2.setTipo("internal");
//		evento2.setTitle("El evento de prueba");
//		evento2.setDescripcion("Que descripcion mas bonita");
//		evento2.setTipo(tipo2);
//		evento2.setColor("red");
//		evento2.setStart(LocalDate.of(2021, 01, 20));
//		evento2.setEnd(LocalDate.of(2021, 01, 19));
//		
//		mockMvc.perform(post("/events/create/{curso}", "A1")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(evento2.toJson())
//				.with(csrf()).sessionAttr("type","profesor"))
//		.andExpect(status().isNonAuthoritativeInformation())
//		.andExpect(jsonPath("$[0].field", is("start")));
//	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testCreateEventWithErrorsNullTitle() throws Exception {
//		Evento evento2 = new Evento();
//		TipoEvento tipo2 = new TipoEvento();
//		tipo2.setTipo("internal");
//		evento2.setDescripcion("Que descripcion mas bonita");
//		evento2.setTipo(tipo2);
//		evento2.setColor("red");
//		evento2.setStart(LocalDate.of(2021, 01, 20));
//		evento2.setEnd(LocalDate.of(2021, 01, 21));
//		
//		mockMvc.perform(post("/events/create/{curso}", "A1")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(evento2.toJson())
//				.with(csrf()).sessionAttr("type","profesor"))
//		.andExpect(status().isNonAuthoritativeInformation())
//		.andExpect(jsonPath("$[0].field", is("title")));
//	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testCreateEventWithErrorsNullCurso() throws Exception {
//		Evento evento2 = new Evento();
//		TipoEvento tipo2 = new TipoEvento();
//		tipo2.setTipo("internal");
//		evento2.setTitle("El evento de prueba");
//		evento2.setDescripcion("Que descripcion mas bonita");
//		evento2.setTipo(tipo2);
//		evento2.setColor("red");
//		evento2.setStart(LocalDate.of(2021, 01, 20));
//		evento2.setEnd(LocalDate.of(2021, 01, 21));
//		
//		mockMvc.perform(post("/events/create/{curso}", "null")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(evento2.toJson())
//				.with(csrf()).sessionAttr("type","profesor"))
//		.andExpect(status().isNonAuthoritativeInformation())
//		.andExpect(jsonPath("$[0].field", is("curso")));
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateEventWithErrorsExistEvent() throws Exception {
		given(eventoService.existEvent(any())).willReturn(true);
		
		mockMvc.perform(post("/events/create/{curso}", "A1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(evento.toJson())
				.with(csrf()).sessionAttr("type","profesor"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("The event already exists")));
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testCreateEventWithErrorsExistType() throws Exception {
//		given(eventoService.existEvent(any())).willReturn(false);
//		given(eventoService.assignTypeAndSave(any(), any())).willReturn(false);
//		
//		mockMvc.perform(post("/events/create/{curso}", "A1")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(evento.toJson())
//				.with(csrf()).sessionAttr("type","profesor"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$", is("Type not exist")));
//	}
	
}
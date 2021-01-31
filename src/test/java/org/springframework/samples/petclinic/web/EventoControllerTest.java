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
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.service.EventoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=EventoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class EventoControllerTest {
	
	@MockBean
	private EventoService eventoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Evento evento;
	
	@BeforeEach
	void setup() {
		evento = new Evento();
		Curso curso = new Curso();
		TipoEvento tipo = new TipoEvento();
		tipo.setTipo("internal");
		curso.setCursoDeIngles(TipoCurso.B2);
		evento.setTitle("El evento de prueba");
		evento.setCurso(curso);
		evento.setDescripcion("Que descripcion mas bonita");
		evento.setStart(LocalDate.of(2019, 10, 10));
		evento.setEnd(LocalDate.of(2020, 01, 19));
		evento.setTipo(tipo);
		evento.setColor("red");
		evento.setId(1);
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAllEvents() throws Exception {
		given(this.eventoService.getAll()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/events/all").sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testSendingNewEventSucces() throws Exception{
//
//		given(eventoService.getEvento(evento.getId())).willReturn(null);
//		
//		mockMvc.perform(post("/create/{curso}")
//				.with(csrf())
//				.param("evento.title", "titulo")
//				.param("evento.curso", "B2")
//				.param("evento.decripcion", "Esta es la descripcion")
//				.param("evento.start", "2020-12-23")
//				.param("evento.end", "2020-12-25")
//				.param("evento.tipo", "internal")
//				.param("evento.color", "red")
//				)
//        .andExpect(status().isOk());
//	}

}

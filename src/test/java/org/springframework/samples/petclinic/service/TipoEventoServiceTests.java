package org.springframework.samples.petclinic.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.TipoEvento;
import org.springframework.samples.petclinic.repository.TipoEventoRepository;

@ExtendWith(MockitoExtension.class)
public class TipoEventoServiceTests {
	
	@Mock
	private TipoEventoRepository tipoEventoRepository;
	
	protected TipoEventoService tipoEventoService;
	
	@BeforeEach
	void setup() {
		tipoEventoService = new TipoEventoService(tipoEventoRepository);
	}
	
	@Test
	void shouldGetType() {
		TipoEvento tipo = new TipoEvento();
		tipo.setTipo("internal");
		Optional<TipoEvento> op = Optional.of(tipo);
		when(tipoEventoRepository.findById(any())).thenReturn(op);
		
		TipoEvento t = tipoEventoService.getType("1");
		
		assertThat(t.getTipo()).isEqualTo("internal");
	}
	
	@Test
	void shouldGetTypeNull() {
		Optional<TipoEvento> op = Optional.empty();
		when(tipoEventoRepository.findById("100")).thenReturn(op);
		
		TipoEvento t = tipoEventoService.getType("100");
		
		assertThat(t).isNull();
	}

}

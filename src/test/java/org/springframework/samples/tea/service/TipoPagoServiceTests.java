package org.springframework.samples.tea.service;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.tea.model.TipoPago;
import org.springframework.samples.tea.repository.TipoPagoRepository;

@ExtendWith(MockitoExtension.class)
public class TipoPagoServiceTests {

	@Mock
	private TipoPagoRepository tipoPagoRepository;

	protected TipoPagoService tipoPagoService;

	@BeforeEach
	void setup() {
		tipoPagoService =new TipoPagoService(tipoPagoRepository);
	}

	@Test
	void shouldGetType() {
		TipoPago t = new TipoPago();
		Optional<TipoPago> opt  = Optional.of(t);
		when(tipoPagoRepository.findById(any())).thenReturn(opt);
		assertThat(tipoPagoService.getType(any())).isEqualTo(opt.get());
	}

	@Test
	void shoulGetTypeNull() {
		Optional<TipoPago> opt  = Optional.empty();
		when(tipoPagoRepository.findById(any())).thenReturn(opt);
		assertThat(tipoPagoService.getType(any())).isNull();
	}

}

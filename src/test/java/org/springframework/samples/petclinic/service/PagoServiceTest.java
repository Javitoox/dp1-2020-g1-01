package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PagoServiceTest {
	
	private static final String CONCEPTO = "Matricula";	
	private static List<Alumno> alumnosPagados;
	private static List<Alumno> alumnosMorosos;

	private static Pago p;
	
	@Mock
	PagoRepository pagoRepository;
	protected PagoService pagoService;
	
	@BeforeAll
	void data() {
		p = new Pago();
		p.setId(1);
		p.setTipo("Bizum");
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());
		
		Alumno a1 = new Alumno();
		Alumno a2 = new Alumno();
		Alumno m1 = new Alumno();
		Alumno m2 = new Alumno();
		alumnosPagados = new ArrayList<>();
		alumnosMorosos = new ArrayList<>();
		alumnosPagados.add(a1);
		alumnosPagados.add(a2);
		
		alumnosMorosos.add(m1);
		alumnosMorosos.add(m2);
	}
	
	@BeforeEach
	void setup() {
		pagoService = new PagoService(pagoRepository);		
	}
	
	@Test
	void testPaymentListIsNotEmpty() {
		when(pagoRepository.findStudentsByPago(CONCEPTO)).thenReturn(alumnosPagados);
		assertThat(pagoService.getStudentsByPayment(CONCEPTO)).isNotEmpty();
	}
	
	@Test
	void testPaymentsNotPaidListIsNotEmpty() {
		when(pagoRepository.findStudentsByPago(CONCEPTO)).thenReturn(alumnosMorosos);
		assertThat(pagoService.getStudentsByPayment(CONCEPTO)).isNotEmpty();
	}
	
	@Test
	void testRegisterAPayment() {
		pagoService.savePayment(p);
		verify(pagoRepository, times(1)).save(any());		
	} 

}

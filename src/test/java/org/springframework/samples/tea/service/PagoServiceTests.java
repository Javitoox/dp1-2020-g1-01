package org.springframework.samples.tea.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Pago;
import org.springframework.samples.tea.model.TipoPago;
import org.springframework.samples.tea.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PagoServiceTests {

	private static final String CONCEPTO = "Matricula";
	private static List<Alumno> ningunPago;
	private static List<Alumno> alumnosPagados;
	private static List<Alumno> alumnosMorosos;
	private static Set<String> conceptos;
	private static List<String> alumnos;


	private static Pago p;

	@Mock
	PagoRepository pagoRepository;
	protected PagoService pagoService;

	@BeforeAll
	void data() {
		p = new Pago();
		TipoPago t = new TipoPago();
		p.setId(1);
		p.setTipo(t);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());

		Alumno a1 = new Alumno();
		Alumno a2 = new Alumno();
		Alumno m1 = new Alumno();
		Alumno m2 = new Alumno();
		ningunPago = new ArrayList<>();
		alumnosPagados = new ArrayList<>();
		alumnosMorosos = new ArrayList<>();
		alumnosPagados.add(a1);
		alumnosPagados.add(a2);

		alumnosMorosos.add(m1);
		alumnosMorosos.add(m2);

		conceptos = new HashSet<>();
		conceptos.add(CONCEPTO);

		alumnos = new ArrayList<>();
		alumnos.add(a1.getNickUsuario());
		alumnos.add(a2.getNickUsuario());
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
	void testPaymentListIsEmpty() {
		when(pagoRepository.findStudentsByPago(CONCEPTO)).thenReturn(ningunPago);
		assertThat(pagoService.getStudentsByPayment(CONCEPTO)).isEmpty();
	}

	@Test
	void testPaymentsNotPaidListIsNotEmpty() {
		when(pagoRepository.findStudentsByPago(CONCEPTO)).thenReturn(alumnosMorosos);
		assertThat(pagoService.getStudentsByPayment(CONCEPTO)).isNotEmpty();
	}

	@Test
	void testPaymentsNotPaidListIsEmpty() {
		when(pagoRepository.findStudentsByPago(CONCEPTO)).thenReturn(new ArrayList<>());
		assertThat(pagoService.getStudentsByPayment(CONCEPTO)).isEmpty();
	}

	@Test
	void testRegisterAPayment() {
		pagoService.savePayment(p);
		verify(pagoRepository).save(p);
	}

	@Test
	void testAllPaymentNames() {
		when(pagoRepository.allPagos()).thenReturn(conceptos);
		Set<String> names = pagoService.getAllPayments();
		assertThat(names).hasSize(1);
		assertThat(names.iterator().next()).isEqualTo(CONCEPTO);
	}

	@Test
	void testStudentsByNoPagoIsNotEmpty() {
		when(pagoRepository.findStudentByNoPago(CONCEPTO)).thenReturn(alumnosMorosos);
		assertThat(pagoService.getStudentsNoPayment(CONCEPTO)).isNotEmpty();
	}

	@Test
	void testStudentsByNoPagoIsEmpty() {
		when(pagoRepository.findStudentByNoPago(CONCEPTO)).thenReturn(new ArrayList<>());
		assertThat(pagoService.getStudentsNoPayment(CONCEPTO)).isEmpty();
	}

	@Test
	void testStudentsNamesByNoPago() {
		when(pagoRepository.findNameStudentByNoPago()).thenReturn(alumnos);
		List<String> names = pagoService.getNameStudentByNoPago();
		assertThat(names).hasSize(2);
		assertThat(names).isNotEmpty();
	}

	@Test
	void testPaymentsNamesNotMadeByStudent() {
		List<String> concepts = conceptos.stream().collect(Collectors.toList());
		when(pagoRepository.findNoPaymentByStudent("Javiel")).thenReturn(concepts);
		List<String> names  = pagoService.getNoPaymentByStudent("Javiel");
		assertThat(names).hasSize(1);
		assertThat(names).isNotEmpty();
	}

	@Test
	void testPaymentsMadeByStudent() {
		List<Pago> pagos = new ArrayList<>();
		pagos.add(p);
		when(pagoRepository.findPaymentsByStudent("Javiel")).thenReturn(pagos);
		List<Pago> allPagosMade = pagoService.getPaymentsByStudent("Javiel");
		assertThat(allPagosMade).hasSize(1);
		assertThat(allPagosMade).isNotEmpty();
	}

	@Test
	void testPaymentsMadeByStudentIsEmpty() {
		List<Pago> pagos = new ArrayList<>();
		when(pagoRepository.findPaymentsByStudent("Javiel")).thenReturn(pagos);
		List<Pago> allPagosMade = pagoService.getPaymentsByStudent("Javiel");
		assertThat(allPagosMade).hasSize(0);
		assertThat(allPagosMade).isEmpty();
	}
}

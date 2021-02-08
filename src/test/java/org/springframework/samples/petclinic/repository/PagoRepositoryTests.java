package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.TipoPago;


@DataJpaTest
public class PagoRepositoryTests {
	
	private static Pago pago;
	private static final String CONCEPTO= "Primera matricula";
	
	@Autowired
	protected PagoRepository pagoRepository;
	@Autowired
	protected AlumnoRepository alumnoRepository;
	
	@Autowired
	protected TipoPagoRepository tipoPagoRepository;
	
	@BeforeEach
	void setup() {
		pago = new Pago();
		pago.setId(1);
		pago.setConcepto(CONCEPTO);
		pago.setFecha(LocalDate.of(2017, 11, 11));
		pago.setTipo(tipoPagoRepository.findById("Bizum").orElse(null));
		Alumno a = new Alumno();
		a.setNickUsuario("Bebelyn");
		a.setNombreCompletoUsuario("Evelyn Yugsi");
		a.setCorreoElectronicoUsuario("bebelyn@gmail.com");
		a.setFechaNacimiento(LocalDate.of(1999, 11, 22));
		a.setDniUsuario("11111111A");
		a.setNumTelefonoUsuario("611111111");
		a.setDireccionUsuario("Macarena");
		a.setContraseya("EvelynP091");
		a.setFechaMatriculacion(LocalDate.of(2020, 01, 11));
		
		Alumno a1 = new Alumno();
		a1.setNickUsuario("Javiel");
		a1.setNombreCompletoUsuario("Javier Vila");
		a1.setCorreoElectronicoUsuario("javiel@gmail.com");
		a1.setFechaNacimiento(LocalDate.of(2000, 11, 22));
		a1.setDniUsuario("11111111B");
		a1.setNumTelefonoUsuario("611111112");
		a1.setDireccionUsuario("Triana");
		a1.setContraseya("Javiel123K");
		a1.setFechaMatriculacion(LocalDate.of(2020, 01, 11));
		alumnoRepository.save(a1);

		
		pago.setAlumnos(a);
		alumnoRepository.save(a);

		pagoRepository.save(pago);
	}
	
	@Test
	public void shouldReturnAllPaymentNames() {
		Set<String> allPaymentNames = pagoRepository.allPagos();
		assertThat(allPaymentNames).isNotEmpty();
	}
	
	@Test
	public void shouldReturnAllStudents() {
		List<Alumno> alumns = pagoRepository.findStudentsByPago(CONCEPTO);
		assertThat(alumns).isNotEmpty();
	}

	@Test
	public void shouldReturnAllStudentsWhoDoesntPaid() {
		List<Alumno> alumns = pagoRepository.findStudentByNoPago(CONCEPTO);
		assertThat(alumns.size()).isGreaterThan(0);
	}
	
	@Test 
	public void shoulReturnAllStudentNamesWhoDoesntPaid() {
		List<String> alumns = pagoRepository.findNameStudentByNoPago();
		assertThat(alumns.size()).isGreaterThan(0);
	}
	
	@Test
	public void shouldReturnNoPaymentsByStudent() {
		List<String> conceptos = pagoRepository.findNoPaymentByStudent("Javiel");
		assertThat(conceptos.size()).isGreaterThan(0);
	}
	
	@Test 
	public void shoulReturnPaymentsByStudent() {
		List<Pago> pagosByStudent = pagoRepository.findPaymentsByStudent("Bebelyn");
		assertThat(pagosByStudent.size()).isGreaterThan(0); 
	}
	
}

package org.springframework.samples.petclinic.repository;


import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;

@DataJpaTest
public class SolicitudRepositoryTests {

	private static Alumno a;

	@Autowired
	protected SolicitudRepository solicitudRepository;

	@Autowired
	protected AlumnoRepository alumnoRepository;


	@BeforeEach
	void data() {
		a = new Alumno();
		a.setNickUsuario("marrambla");
		a.setDniUsuario("99876566W");
		a.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		a.setNombreCompletoUsuario("Maria Dolores Garcia");
		a.setDireccionUsuario("Triana de Sevilla");
		a.setCorreoElectronicoUsuario("mariadeldolor@gmail.com");
		a.setContraseya("Pollito009");
		a.setNumTelefonoUsuario("698898989");
		a.setFechaSolicitud(LocalDate.of(2019, 03, 13));
		alumnoRepository.save(a);
	}

	@Test
	void testReturnStudentsNotAcceptedYet() {
		List<Alumno> alumnosPending = solicitudRepository.findStudentsNotAcceptedYet();
        assertThat(alumnosPending.size()).isGreaterThan(0);
	}

}
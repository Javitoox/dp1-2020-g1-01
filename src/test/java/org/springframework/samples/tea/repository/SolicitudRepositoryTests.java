package org.springframework.samples.tea.repository;


import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.Alumno;

@DataJpaTest
public class SolicitudRepositoryTests {

	private static Alumno a;

	@Autowired
	protected SolicitudRepository solicitudRepository;

	@Autowired
	protected AlumnoRepository alumnoRepository;


	@BeforeEach
	@Transactional
	void data() throws DataAccessException{
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

package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class TutorServiceTests {
	
	@Autowired
	protected TutorService tutorService;
	
	@BeforeAll
	@Transactional
	void insertTutor() {
		Tutor tutor = new Tutor();
		tutor.setContraseyaTutor("EyEyHola6");
		tutor.setCorreoElectronicoUsuarioTutor("pedro@gmail.com");
		tutor.setDireccionUsuarioTutor("Calle Lora");
		tutor.setDniUsuarioTutor("23232323H");
		tutor.setFechaNacimientoTutor("12/5/1980");
		tutor.setNickUsuarioTutor("PedroGar");
		tutor.setNombreCompletoUsuarioTutor("Pedro García");
		tutor.setNumTelefonoUsuarioTutor("676767453");
		tutorService.insert(tutor);
	}
	
	@Test
	void shouldGetAListWithTutores() {
		List<Tutor> tutores = tutorService.getAllTutores();
		assertThat(tutores.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldGetATutor() {
		Tutor tutor = tutorService.getTutor("PedroGar");
		assertThat(tutor).isNotNull();
	}

}

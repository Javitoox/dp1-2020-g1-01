package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioServiceTests {
	private static final Logger log = LoggerFactory.logger(UsuarioServiceTests.class);
	
	@Autowired
	protected UsuarioService usuarioService;
	
	@Autowired
	protected AlumnoService alumnoService;
	
	@Autowired
	protected TutorService tutorService;
	
	@BeforeAll
	@Transactional
	void insertAlumnoTutor() {
		Alumno alumno = new Alumno();
		alumno.setContraseya("HolaBuenas777");
		alumno.setCorreoElectronicoUsuario("javikuka7@gmail.com");
		alumno.setDireccionUsuario("Calle error");
		alumno.setDniUsuario("76766776Y");
		alumno.setFechaMatriculacion("13/8/2020");
		alumno.setFechaNacimiento("13/8/2000");
		alumno.setNickUsuario("JaviMartinez");
		alumno.setNombreCompletoUsuario("Javi Martínez");
		alumno.setNumTelefonoUsuario("635096767");
		alumno.setNumTareasEntregadas(4);
		alumnoService.saveAlumno(alumno);
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
	@Transactional
	void typeShouldBeIntegrante() {
		String nickUsuario = "JaviMartinez";
		String contraseya = "HolaBuenas777";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		
		assertThat(type).isEqualTo("integrante");
	}
	
	@Test
	void typeShouldBeUsernameNotExist() {
		String nickUsuario = "--------ImposibleQueExistaError---------";
		String contraseya = "prueba";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		
		assertThat(type).isEqualTo("Username not exist");
	}
	
	@Test
	@Transactional
	void typeShouldBeTutor() {
		String nickUsuario = "PedroGar";
		String contraseya = "EyEyHola6";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		
		assertThat(type).isEqualTo("tutor");
	}
	
	@Test
	@Transactional
	void typeShouldBeIncorrectPassword() {
		String nickUsuario = "JaviMartinez";
		String contraseya = "noExiste";
		String contraseyaNoExistente = contraseya.concat("noExiste");
		String type = usuarioService.typeOfUser(nickUsuario, contraseyaNoExistente);
		
		assertThat(contraseya).isNotEqualTo(contraseyaNoExistente);
		assertThat(type).isEqualTo("Incorrect password");
	}

}

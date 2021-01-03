package org.springframework.samples.petclinic.service.old;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioServiceTests {
	
	@Autowired
	protected UsuarioService usuarioService;
	
	@Autowired
	protected SolicitudService solicitudService;
	
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
		alumno.setFechaMatriculacion(LocalDate.parse("2020-12-09"));
		alumno.setFechaSolicitud(LocalDate.parse("2020-12-02"));
		alumno.setFechaNacimiento(LocalDate.parse("2000-12-02"));
		alumno.setNickUsuario("JaviMartinez");
		alumno.setNombreCompletoUsuario("Javi Martínez");
		alumno.setNumTelefonoUsuario("635096767");
		alumno.setNumTareasEntregadas(4);
		alumnoService.saveAlumno(alumno);
		Tutor tutor = new Tutor();
		tutor.setContraseya("EyEyHola6");
		tutor.setCorreoElectronicoUsuario("pedro@gmail.com");
		tutor.setDireccionUsuario("Calle Lora");
		tutor.setDniUsuario("23232323H");
		tutor.setFechaMatriculacion(LocalDate.parse("2020-12-09"));
		tutor.setFechaSolicitud(LocalDate.parse("2020-12-02"));
		tutor.setFechaNacimiento(LocalDate.parse("2000-12-02"));
		tutor.setNickUsuario("PedroGar");
		tutor.setNombreCompletoUsuario("Pedro García");
		tutor.setNumTelefonoUsuario("676767453");
		tutorService.saveTutor(tutor);
	}
	
	@Test
	@Transactional
	void testTypeShouldntBeIntegrante() {
		String nickUsuario = "JaviMartinez";
		String contraseya = "HolaBuenas777";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		
		assertThat(type).isNotEqualTo("integrante");
	}
	
	@Test
	void testTypeShouldBeUsernameNotExist() {
		String nickUsuario = "--------ImposibleQueExistaError---------";
		String contraseya = "prueba";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		
		assertThat(type).isEqualTo("Username not exist");
	}
	
	@Test
	@Transactional
	void testTypeShouldBeTutor() {
		String nickUsuario = "PedroGar";
		String contraseya = "EyEyHola6";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		
		assertThat(type).isEqualTo("tutor");
	}
	
	@Test
	@Transactional
	void testTypeShouldBeIncorrectPassword() {
		String nickUsuario = "JaviMartinez";
		String contraseya = "noExiste";
		String contraseyaNoExistente = contraseya.concat("noExiste");
		String type = usuarioService.typeOfUser(nickUsuario, contraseyaNoExistente);
		
		assertThat(contraseya).isNotEqualTo(contraseyaNoExistente);
		assertThat(type).isEqualTo("Incorrect password");
	}

}

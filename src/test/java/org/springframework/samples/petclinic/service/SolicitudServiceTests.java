package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class SolicitudServiceTests {
	
	@Autowired
	protected SolicitudService solicitudService;
	
	@Autowired
	protected AlumnoService alumnoService;
	
	@BeforeAll
	@Transactional
	void insertSolicitud() {
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
		solicitudService.insertByNick(alumno.getNickUsuario());
	}
	
	@Test
	void shouldGetAListWithSolicitudes() {
		Collection<Solicitud> solicitudes = solicitudService.getAllSolicitudes();
		assertThat(solicitudes.size()).isGreaterThan(0);
	}

}

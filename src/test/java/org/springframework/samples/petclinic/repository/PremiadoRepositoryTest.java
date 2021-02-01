package org.springframework.samples.petclinic.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.model.WallOfFame;

@DataJpaTest
public class PremiadoRepositoryTest {

	private static Premiado p;
	private static Alumno a;
	private static WallOfFame wall;
	
	@Autowired
	protected PremiadoRepository premiadoRepository;
	
	@Autowired
	protected AlumnoRepository alumnoRepository;
	
	@Autowired
	protected WallOfFameRepository wallOfFameRepository;
	
	
	@BeforeEach
	void data() {
		a = new Alumno();
		a.setNickUsuario("marrambla");
		a.setFechaMatriculacion(LocalDate.now());
		a.setDniUsuario("99876566W");
		a.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		a.setNombreCompletoUsuario("Maria Dolores Garcia");
		a.setDireccionUsuario("Triana de Sevilla");
		a.setCorreoElectronicoUsuario("mariadeldolor@gmail.com");
		a.setContraseya("Pollito009");
		a.setNumTelefonoUsuario("698898989");
		Alumno alumno = alumnoRepository.save(a);
		
		wall = new WallOfFame();
		wall.setFechaWall("2022-W50");	//2022 para que sea el ultimo wall añadido
		WallOfFame wallofFame = wallOfFameRepository.save(wall);
		
		p = new Premiado();
		p.setAlumnos(alumno);
		p.setDescripcion("The best student");
		p.setFoto("photo");
		p.setWalloffames(wallofFame);
		premiadoRepository.save(p);
	}
	
	
	@Test
	void testReturnPremiadosPorFecha() {
		List<Premiado> premiados = premiadoRepository.premiadosPorFecha("2022-W50");
		assertTrue(premiados.size() > 0);
	}
	
	@Test
	void testReturnLastWallOfFame() {
		String lastWeek = premiadoRepository.lastWallOfFame();
		assertEquals("2022-W50", lastWeek);
	}
	
	@Test
	void testReturnNumApariciones() {
		Integer numApariciones = premiadoRepository.numAparicionesEnFecha("2022-W50", "marrambla");
		assertTrue(numApariciones == 1);
	}
	
	
	
	
	
	
	
	
}

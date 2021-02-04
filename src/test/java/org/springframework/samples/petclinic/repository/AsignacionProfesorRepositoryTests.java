package org.springframework.samples.petclinic.repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Profesor;

@DataJpaTest
public class AsignacionProfesorRepositoryTests {
	
	private static AsignacionProfesor asig;
	private static Grupo g;
	private static final String NICK_PROFESOR = "Bebelyn";
	
	@Autowired
	protected AsignacionProfesorRepository asignacionRepository;
	@Autowired
	protected ProfesorRepository profesorRepository;
	@Autowired
	protected GrupoRepository grupoRepository;
	
	@BeforeEach
	void setup() {
		
		asig = new AsignacionProfesor();
		g = new Grupo();
		g.setNombreGrupo("Grupo1");
		grupoRepository.save(g);
		
		Grupo gVacio = new Grupo();
		gVacio.setNombreGrupo("Grupo2");
		grupoRepository.save(gVacio);

		Profesor a = new Profesor();
		a.setNickUsuario(NICK_PROFESOR);
		a.setNombreCompletoUsuario("Evelyn Yugsi");
		a.setCorreoElectronicoUsuario("bebelyn@gmail.com");
		a.setFechaNacimiento(LocalDate.of(1999, 11, 22));
		a.setDniUsuario("11111111A");
		a.setNumTelefonoUsuario("611111111");
		a.setDireccionUsuario("Macarena");
		a.setContraseya("EvelynP091");
		profesorRepository.save(a);

		
		AsignacionProfesorKey id = new AsignacionProfesorKey();
		id.setNickProfesor(NICK_PROFESOR);
		id.setNombreGrupo("Grupo1");		
		
		asig.setId(id);
		asig.setProfesor(a);
		asig.setGrupo(g);
		asig.setFecha(LocalDate.of(2020, 10, 8));
		asignacionRepository.save(asig);
		List<AsignacionProfesor> asignaciones = new ArrayList<>();
		asignaciones.add(asig);
		g.setAsignaciones(asignaciones);
		a.setAsignaciones(asignaciones);
		
		
	}
	
	@Test
	void shouldReturnAllAsignations() {
		List<AsignacionProfesor> asigns = asignacionRepository.getAsignacionesByProfesor(NICK_PROFESOR);
		assertThat(asigns.size()).isGreaterThan(0);
	}
	
	@Test
	void shoulReturnAllFreeGroupsNames(){
		List<String> freeGroups = asignacionRepository.getFreeGroups();
		assertThat(freeGroups.size()).isGreaterThan(0);
	}
}

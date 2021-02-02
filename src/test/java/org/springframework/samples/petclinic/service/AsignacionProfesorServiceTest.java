package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.repository.AsignacionProfesorRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AsignacionProfesorServiceTest {
	
	private static List<AsignacionProfesor> asignacionesProf;
	private static List<String> gruposLibres;
	private static final String NICK_PROFESOR = "Maroto";
	@Mock
	private AsignacionProfesorRepository asignacionProfRepository;
	
	protected AsignacionProfesorService asignacionProfService;
	
	
	@BeforeAll
	void data() {
		asignacionesProf = new ArrayList<>();
		AsignacionProfesor asig = new AsignacionProfesor();
		asignacionesProf.add(asig);
		
		gruposLibres = new ArrayList<>();
		gruposLibres.add("Grupo C");

	}
	
	@BeforeEach
	void setup() {
		asignacionProfService = new AsignacionProfesorService(asignacionProfRepository);
	}
	
	@Test
	void shouldShowAListWithAsignationsIsNotEmpty() {
		when(asignacionProfRepository.getAsignacionesByProfesor(NICK_PROFESOR)).thenReturn(asignacionesProf);
		assertThat(asignacionProfService.getAllAsignacionesByUser(NICK_PROFESOR)).isNotEmpty();
	}

	@Test
	void shouldSaveATeacherAsignation() {
		AsignacionProfesor asig = new AsignacionProfesor();
		Grupo g = new Grupo();
		Profesor p = new Profesor();
		asig.setFecha(LocalDate.now());
		asig.setGrupo(g);
		asig.setProfesor(p);
		
		asignacionProfService.saveAsignacion(asig);
		verify(asignacionProfRepository).save(asig);
	}
	
	@Test
	void shouldGetNameOfAllFreeGroups() {
		when(asignacionProfRepository.getFreeGroups()).thenReturn(gruposLibres);
		assertThat(asignacionProfService.getFreeGroups()).isNotEmpty();
	}
}

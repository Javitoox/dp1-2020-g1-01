package org.springframework.samples.tea.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.tea.model.AsignacionProfesor;
import org.springframework.samples.tea.model.AsignacionProfesorKey;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.model.Profesor;
import org.springframework.samples.tea.repository.AsignacionProfesorRepository;

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
	void shouldShowAListWithAsignationsIsEmpty() {
		when(asignacionProfRepository.getAsignacionesByProfesor(NICK_PROFESOR)).thenReturn(new ArrayList<>());
		assertThat(asignacionProfService.getAllAsignacionesByUser(NICK_PROFESOR)).isEmpty();
	}

	@Test
	void shouldSaveATeacherAsignation() {
		AsignacionProfesor asig = new AsignacionProfesor();
		Grupo g = new Grupo();
		Profesor p = new Profesor();
		asig.setFecha(LocalDate.of(2019, 1, 2));
		asig.setGrupo(g);
		asig.setProfesor(p);

		asignacionProfService.saveAsignacion(asig);
		verify(asignacionProfRepository).save(asig);
	}


	@Test
	void shoulDeleteAnAsignation() {
		AsignacionProfesorKey id = new AsignacionProfesorKey();
		id.setNickProfesor(NICK_PROFESOR);
		id.setNombreGrupo("Tercero A");
		AsignacionProfesor asig = new AsignacionProfesor();
		Grupo g = new Grupo();
		Profesor p = new Profesor();
		asig.setId(id);
		asig.setFecha(LocalDate.of(2019, 2, 2));
		asig.setGrupo(g);
		asig.setProfesor(p);
		asignacionProfService.deleteAsignacion(id);
		verify(asignacionProfRepository, times(1)).deleteById(id);
	}

	@Test
	void shouldReturnATeacherAsignation() {
		AsignacionProfesorKey id = new AsignacionProfesorKey();
		id.setNickProfesor(NICK_PROFESOR);
		id.setNombreGrupo("Tercero A");
		Grupo g = new Grupo();

		g.setNombreGrupo("Tercero A");
		Profesor p = new Profesor();
		p.setNickUsuario(NICK_PROFESOR);

		AsignacionProfesor asig = new AsignacionProfesor();
		asig.setId(id);
		asig.setFecha(LocalDate.of(2019, 2, 2));
		asig.setGrupo(g);
		asig.setProfesor(p);
		Optional<AsignacionProfesor> opt = Optional.of(asig);
		when(asignacionProfRepository.findById(id)).thenReturn(opt);
		assertThat(asignacionProfService.findAsignacionProfesor(id)).isEqualTo(opt.get());

	}

	@Test
	void shoulReturnAsignationsNamesByGroup() {
		List<String> asignaciones = new ArrayList<>();
		Grupo g = new Grupo();
		g.setNombreGrupo("B2 A");
		when(asignacionProfRepository.getAsignacionesByGroup(g.getNombreGrupo())).thenReturn(asignaciones);
		assertThat(asignacionProfService.findAsignacionesByGroup(g.getNombreGrupo())).isEqualTo(asignaciones);
	}

	@Test
	void shoulReturnAsignationsNamesByGroupIsEmpty() {
		when(asignacionProfRepository.getAsignacionesByGroup(any())).thenReturn(new ArrayList<>());
		assertThat(asignacionProfService.findAsignacionesByGroup("B2 A")).isEmpty();
	}
}

package org.springframework.samples.petclinic.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TutorServiceTests {
	
	private static Tutor t;
	
	@Mock
	private TutorRepository tutorRepository;
	
	@InjectMocks
	protected TutorService tutorService;
	
	@BeforeAll
	void data() {
		t = new Tutor();
		t.setNickUsuario("Roberto23");
	}
	
	@Test
	void shouldFindTutors() {
        List<Tutor> tutores = new ArrayList<Tutor>();
        tutores.add(t);
        when(tutorRepository.findAll()).thenReturn(tutores);
        
        List<Tutor> ts = tutorService.getAllTutores();
        
        assertThat(ts).hasSize(1);
        Tutor t = ts.iterator().next();
        assertThat(t.getNickUsuario()).isEqualTo("Roberto23");
        assertThat(t.getFechaMatriculacion()).isNull();
	}
	
	@Test
	void shouldGetTutor() {
		when(tutorRepository.findByNick("Roberto23")).thenReturn(t);
		
		Tutor tutor = tutorService.getTutor("Roberto23");
		
		assertThat(tutor).isNotNull();
		assertThat(tutor.getNickUsuario()).isEqualTo("Roberto23");
	}
	
	@Test
	void shouldntGetTutor() {
		when(tutorRepository.findByNick("Roberto23")).thenReturn(t);
		
		Tutor tutor = null;
		try {
			tutor = tutorService.getTutor("LuisRoberto23");
		}catch(Exception e) {
			assertThat(tutor).isNull();
		}
	}
	
	@Test
	@Transactional
	void shouldSaveTutor() {
		tutorService.saveTutor(t);
		
		verify(tutorRepository, times(1)).save(any());
	}
	
	@Test
	@Transactional
	void shouldDeleteTutor() {
		tutorService.delete("Roberto23");
		
		verify(tutorRepository, times(1)).deleteById(any());
	}
	
}
package org.springframework.samples.petclinic.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
public class SolicitudServiceTests {
	
	@Mock
	private SolicitudRepository solicitudRepository;
	@Mock
	private AlumnoService alumnoService;
	@Mock
	private TutorService tutorService;
	
	@InjectMocks
	protected SolicitudService solicitudService;
	
	@Test
	@Transactional
	void shouldSaveRequestComplete() {
		Solicitud solicitud = new Solicitud();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviKuka7");
		alumno.setFechaSolicitud(LocalDate.now());
		solicitud.setAlumno(alumno);
		Tutor tutor = new Tutor();
		tutor.setNickUsuario("Roberto99");
		tutor.setFechaSolicitud(LocalDate.now());
		solicitud.setTutor(tutor);
		
		solicitudService.saveRequest(solicitud);
		
		verify(alumnoService, times(1)).saveAlumno(any());
		verify(tutorService, times(1)).saveTutor(any());
	}

	@Test
	@Transactional
	void shouldSaveRequestOnlyAlum() {
		Solicitud solicitud = new Solicitud();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviKuka7");
		alumno.setFechaSolicitud(LocalDate.now());
		solicitud.setAlumno(alumno);
		
		solicitudService.saveRequest(solicitud);
		
		verify(alumnoService, times(1)).saveAlumno(any());
		verify(tutorService, times(0)).saveTutor(any());
	}
	
	@Test
	void shouldGetTutor() {
		Tutor tutor = new Tutor();
		tutor.setNickUsuario("Roberto99");
		when(tutorService.getTutor(any())).thenReturn(tutor);
		
		Tutor t = solicitudService.getTutor("Roberto99");
		
		assertThat(t).isNotNull();
		assertThat(t.getNickUsuario()).isEqualTo("Roberto99");
	}
	
	@Test
	void shouldGetAlumno() {
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("Felipe99");
		when(alumnoService.getAlumno(any())).thenReturn(alumno);
		
		Alumno a = solicitudService.getAlumno("Felipe99");
		
		assertThat(a).isNotNull();
		assertThat(a.getNickUsuario()).isEqualTo("Felipe99");
	}
	
}
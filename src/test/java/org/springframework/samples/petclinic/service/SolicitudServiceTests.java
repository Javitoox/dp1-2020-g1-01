package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SolicitudServiceTests {
	
	@Mock
	private SolicitudRepository solicitudRepository;
	@Mock
	private AlumnoService alumnoService;
	@Mock
	private TutorService tutorService;
	
	@InjectMocks
	protected SolicitudService solicitudService;
	
    private static List<Alumno> pendingRequestNotEmpty;	
    private static List<Alumno> pendingRequestEmpty;
    
    private static List<Alumno> listWithStudents;	
    private static List<Alumno> listWithoutStudents;
    
    
	private static Alumno ALUMNO_WITHOUT_TUTOR;
	private static  Alumno ALUMNO_WITH_TUTOR; 
 
		   
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

	@BeforeAll
	void data() {
		ALUMNO_WITHOUT_TUTOR = new Alumno();
		pendingRequestNotEmpty= new ArrayList<Alumno>();
		pendingRequestNotEmpty.add(ALUMNO_WITHOUT_TUTOR);
		
		pendingRequestEmpty = new ArrayList<Alumno>();	
		
		listWithoutStudents = new ArrayList<Alumno>();	
		listWithStudents = new ArrayList<Alumno>();	
		listWithStudents.add(ALUMNO_WITHOUT_TUTOR);
	}
	
	@Test
	void shouldShowPendingRequestNotEmpty() {
		when(solicitudRepository.findStudentsNotAcceptedYet()).thenReturn(pendingRequestNotEmpty);
		List<Alumno> alumnos = solicitudService.getAllSolicitudes();
		assertThat(alumnos.size()).isGreaterThan(0);
	}
	
	@Test
	void shoulShowPendingRequestEmpty() {
		when(solicitudRepository.findStudentsNotAcceptedYet()).thenReturn(pendingRequestEmpty);
		List<Alumno> alumnos = solicitudService.getAllSolicitudes();
		assertThat(alumnos.size()).isEqualTo(0);
	} 
	
	@Test
	void shoulAcceptPendingRequestWithoutTutor() {
		solicitudService.acceptRequest(ALUMNO_WITHOUT_TUTOR);
		verify(alumnoService, times(1)).saveAlumno(any(Alumno.class));
		verify(tutorService, times(0)).saveTutor(any(Tutor.class));
	}
	
	@Test
	void shoulAcceptPendingRequestWithTutorNotExistedYet() {
		ALUMNO_WITH_TUTOR = new Alumno();
		Tutor t = new Tutor();
		ALUMNO_WITH_TUTOR.setTutores(t);
		 
		solicitudService.acceptRequest(ALUMNO_WITH_TUTOR);
		
		verify(alumnoService, times(1)).saveAlumno(any(Alumno.class));
		verify(tutorService, times(1)).saveTutor(any(Tutor.class));
	} 
	
	@Test
	void shoulAcceptPendingRequestWithTutorExistedYet() {
		ALUMNO_WITH_TUTOR = new Alumno();
		Tutor t = new Tutor();
		t.setFechaMatriculacion(LocalDate.now());
		ALUMNO_WITH_TUTOR.setTutores(t);
		
		solicitudService.acceptRequest(ALUMNO_WITH_TUTOR);
		
		verify(alumnoService, times(1)).saveAlumno(any(Alumno.class));
		verify(tutorService, times(0)).saveTutor(any(Tutor.class));
	}
	
	@Test
	void shouldDenyPendingRequestWithoutTutor() {
		solicitudService.declineRequest(ALUMNO_WITHOUT_TUTOR);
		verify(alumnoService, times(1)).deleteStudents(any(Alumno.class));
		verify(tutorService, times(0)).delete(any(String.class));
	}
	 
	@Test
	void shouldDenyPendingRequestWithTutorWithoutMoreStudents() {
		ALUMNO_WITH_TUTOR = new Alumno();
		Tutor t = new Tutor();
		t.setNickUsuario("Manolo234");
		ALUMNO_WITH_TUTOR.setTutores(t);
		when(alumnoService.getAllMyStudents(any(String.class))).thenReturn(listWithoutStudents);
		
		solicitudService.declineRequest(ALUMNO_WITH_TUTOR);
		
		verify(alumnoService, times(1)).deleteStudents(any(Alumno.class));
		verify(tutorService, times(1)).delete(any(String.class));
	}
	
	@Test
	void shouldDenyPendingRequestWithTutorWithMoreStudents() {
		ALUMNO_WITH_TUTOR = new Alumno();
		Tutor t = new Tutor();
		t.setNickUsuario("Manolo234");
		ALUMNO_WITH_TUTOR.setTutores(t);
		when(alumnoService.getAllMyStudents(any(String.class))).thenReturn(listWithStudents);
		
		solicitudService.declineRequest(ALUMNO_WITH_TUTOR);
		
		verify(alumnoService, times(1)).deleteStudents(any(Alumno.class));
		verify(tutorService, times(0)).delete(any(String.class));
	}
	 
	
	
}





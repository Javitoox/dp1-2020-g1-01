package org.springframework.samples.tea.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Feedback;
import org.springframework.samples.tea.model.Material;
import org.springframework.samples.tea.repository.FeedbackRepository;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class FeedbackServiceTests {

	@Mock
    MaterialService materialService;

	@Mock
	FeedbackRepository feedbackRepository;

	@Mock
    AlumnoService alumnoService;

	@InjectMocks
    FeedbackService feedbackService;

	@BeforeEach
	void data() throws IOException {
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//1.pdf");
		MultipartFile pdf = new MockMultipartFile("1.pdf", Files.readAllBytes(rutaCompleta));

	    Path rutaCompleta2 = Paths.get(rutaAbsoluta + "//200.pdf");
		Files.write(rutaCompleta2,pdf.getBytes());
	}

	@Test
	void shouldAñadirAlumnoAMaterial() {
		Alumno a = new Alumno();
		a.setVersion(1);
		when(materialService.findMaterialById(any(Integer.class))).thenReturn(new Material());
		when(alumnoService.getAlumnoByIdOrNif(any(), any())).thenReturn(a);
		feedbackService.añadirAlumnoAMaterial(1, a);
		verify(feedbackRepository, times(1)).save(any(Feedback.class));
	}

	@Test
	void shouldDeleteMaterialWithoutStudents() throws IOException {
		when(materialService.findMaterialById(any(Integer.class))).thenReturn(new Material());
		when(feedbackRepository.findFeedbackByMaterial(any(Integer.class))).thenReturn(new ArrayList<Feedback>());
		feedbackService.deleteMaterial(200);
		verify(feedbackRepository, times(0)).delete(any(Feedback.class));
		verify(materialService, times(1)).delete(any(Material.class));
	}

	@Test
	void shouldDeleteMaterialWithStudent() throws IOException {
		when(materialService.findMaterialById(any(Integer.class))).thenReturn(new Material());
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		feedbacks.add(new Feedback());
		when(feedbackRepository.findFeedbackByMaterial(any(Integer.class))).thenReturn(feedbacks);
		feedbackService.deleteMaterial(200);
		verify(feedbackRepository, times(1)).delete(any(Feedback.class));
		verify(materialService, times(1)).delete(any(Material.class));
	}

	@Test
	void shouldDeleteMaterialWithStudents() throws IOException {
		when(materialService.findMaterialById(any(Integer.class))).thenReturn(new Material());
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		feedbacks.add(new Feedback());
		feedbacks.add(new Feedback());
		feedbacks.add(new Feedback());
		feedbacks.add(new Feedback());
		feedbacks.add(new Feedback());
		when(feedbackRepository.findFeedbackByMaterial(any(Integer.class))).thenReturn(feedbacks);
		feedbackService.deleteMaterial(200);
		verify(feedbackRepository, times(5)).delete(any(Feedback.class));
		verify(materialService, times(1)).delete(any(Material.class));
	}

	@Test
	void shouldCambiarEstadoDoneActividadACompletado() {
		Feedback f = new Feedback();
		f.setCompletado(false);
		Alumno a = new Alumno();
		a.setNumTareasEntregadas(5);
		f.setAlumnos(a);
		when(feedbackRepository.findById(any(Integer.class))).thenReturn(Optional.of(f));
		feedbackService.cambiarEstadoDoneActividad(2);
		verify(alumnoService, times(1)).saveAlumno(a);
		verify(feedbackRepository, times(1)).save(f);
		assertThat(a.getNumTareasEntregadas()).isEqualTo(6);
	}

	@Test
	void shouldCambiarEstadoDoneActividadANoCompletado() {
		Feedback f = new Feedback();
		f.setCompletado(true);
		Alumno a = new Alumno();
		a.setNumTareasEntregadas(5);
		f.setAlumnos(a);
		when(feedbackRepository.findById(any(Integer.class))).thenReturn(Optional.of(f));
		feedbackService.cambiarEstadoDoneActividad(2);
		verify(alumnoService, times(1)).saveAlumno(a);
		verify(feedbackRepository, times(1)).save(f);
		assertThat(a.getNumTareasEntregadas()).isEqualTo(4);
	}

	@Test
	void shouldGetFeedbackByMaterialAndStudent() {
		when(alumnoService.getAlumno(any(String.class))).thenReturn(new Alumno());
		when(materialService.findMaterialById(any(Integer.class))).thenReturn(new Material());
		when(feedbackRepository.getFeedbackByMaterialAndStudent(any(Alumno.class), any(Material.class))).thenReturn(new Feedback());
		feedbackService.getFeedbackByMaterialAndStudent("maribelrb", 1);
		verify(feedbackRepository, times(1)).getFeedbackByMaterialAndStudent(any(Alumno.class), any(Material.class));
	}

	@Test
	void shouldUpdateFeedback() {
		when(feedbackRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Feedback()));
		feedbackService.updateFeedback("Nice pdf file!", 5, 2);
		verify(feedbackRepository, times(1)).save(any(Feedback.class));
	}


}

package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Feedback;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.model.Profesor;

@DataJpaTest
public class FeedbackRepositoryTests {
	private Material m;
	private Profesor profesor;
	private Alumno a;
	private Feedback f;
	private Material material;
	private Alumno alumno;

	
	@Autowired
	MaterialRepository materialRepository;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	ProfesorRepository profesorRepository;
	
	@Autowired
	AlumnoRepository alumnoRepository;
	
	@Autowired
    TipoMaterialRepository tipoMaterialRepository;
	
	@BeforeEach
	void data() {
		m = new Material();
		m.setFechaSubida(LocalDate.of(2000, 01, 12));
		m.setNombreMaterial("Workbook");
		m.setTipoMaterial(tipoMaterialRepository.findById("Homework").get());
		
		profesor = new Profesor();
		profesor.setContraseya("NahDeLocos88");
		profesor.setNickUsuario("marrambla99");
		profesor.setDniUsuario("99876566W");
		profesor.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		profesor.setNombreCompletoUsuario("Maria Dolores Garcia");
		profesor.setDireccionUsuario("Triana de Sevilla");
		profesor.setCorreoElectronicoUsuario("mariadeldolor@gmail.com");
		profesor.setNumTelefonoUsuario("698898989");
		
		Profesor p = profesorRepository.save(profesor);
		m.setProfesores(p);
		material = materialRepository.save(m);
		
		a = new Alumno();
		a.setNickUsuario("marrambla");
		a.setFechaMatriculacion(LocalDate.now());
		a.setDniUsuario("99876566T");
		a.setFechaNacimiento(LocalDate.of(2000, 06, 22));
		a.setNombreCompletoUsuario("Maria Dolores Garcia");
		a.setDireccionUsuario("Triana de Sevilla");
		a.setCorreoElectronicoUsuario("mariadeldolorr@gmail.com");
		a.setContraseya("Pollito009");
		a.setNumTelefonoUsuario("698898980");
		
		alumno = alumnoRepository.save(a);
		
		f = new Feedback();
		f.setAlumnos(alumno);
		f.setMateriales(material);
		f.setComentario("A good way to learn");
		f.setValoracion(5);
		f.setCompletado(true);
		f.setDiaEntrega(LocalDate.of(2019, 03, 13));
		
		feedbackRepository.save(f);
		
	}
	
	@Test
	void testReturnFeedbackByMaterial() {
		List<Feedback> feedbacks = feedbackRepository.findFeedbackByMaterial(material.getId());
		assertThat(feedbacks.size()).isGreaterThan(0);
	}
	
	@Test
	void testReturnFeedbackByMaterialAndStudent() {
		Feedback fb = feedbackRepository.getFeedbackByMaterialAndStudent(alumno, material);
		assertThat(fb).isNotNull();
	}
} 

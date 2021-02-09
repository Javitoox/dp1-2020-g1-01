package org.springframework.samples.tea.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Feedback;
import org.springframework.samples.tea.model.Material;
import org.springframework.samples.tea.model.Profesor;

@DataJpaTest
public class MaterialRepositoryTests {

	private Material m;
	private Profesor profesor;
	private Alumno a;
	private Feedback f;
	private Material material;


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

	}

	@Test
	void testReturnMaterialByProfesor() {
		List<Material> materiales = materialRepository.getMaterialByProfesor(profesor.getNickUsuario());
		assertThat(materiales.size()).isGreaterThan(0);
	}

	@Test
	void testReturnMaterialByStudent() {
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

		Alumno alumno = alumnoRepository.save(a);

		f = new Feedback();
		f.setAlumnos(alumno);
		f.setMateriales(material);
		f.setComentario("A good way to learn");
		f.setValoracion(5);
		f.setCompletado(true);
		f.setDiaEntrega(LocalDate.of(2019, 03, 13));

		feedbackRepository.save(f);

		List<Material> materiales = materialRepository.getMaterialByAlumno(alumno.getNickUsuario());
		assertThat(materiales.size()).isGreaterThan(0);
	}


}

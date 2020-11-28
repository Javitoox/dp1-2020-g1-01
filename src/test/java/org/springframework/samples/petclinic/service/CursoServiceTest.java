package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class CursoServiceTest {

	@Autowired
	protected CursoService cursoService;
	
	@BeforeAll
	@Test
	void testCreateCourse() {
		Curso c = new Curso();
		c.setCursoDeIngles("A1");
		Curso newCourse = cursoService.saveNewCourse(c);
		assertNotNull(newCourse);
	}
	
	@Test
	void testDeleteCourse() {
		Curso c = cursoService.getCourseById("A1").get();
		cursoService.deleteCourse(c);
		Optional<Curso> courseDeleted = cursoService.getCourseById("A1");
		assertFalse(courseDeleted.isPresent());
	} 
}

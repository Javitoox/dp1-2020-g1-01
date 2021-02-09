package org.springframework.samples.tea.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.tea.service.TutorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/tutores")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TutorController {

	private TutorService tutorService;
	@Autowired
	public TutorController(TutorService tutorService) {
		this.tutorService = tutorService;
	}

	@GetMapping("/teacherByGroup/{nombreGrupo}")
	public ResponseEntity<?> getTeacherByGroup(@PathVariable("nombreGrupo") String nombreGrupo){
		List<String> correoProfesores = tutorService.getTeacherByGroup(nombreGrupo);
		log.info("Email de los profesores del grupo : "+nombreGrupo+": "+correoProfesores);
		return ResponseEntity.ok(correoProfesores);
	}
}

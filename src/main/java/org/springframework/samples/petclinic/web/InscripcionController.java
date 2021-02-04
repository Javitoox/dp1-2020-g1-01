package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.InscripcionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/inscriptions")
public class InscripcionController {
	
	private final InscripcionService inscripcionService;
	
	@Autowired
	public InscripcionController(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}
	
	@PutMapping("/join/{id}/{nick}") //alumno
	public ResponseEntity<?> join(@PathVariable("id") Integer id, @PathVariable("nick") String nick){
		Boolean succes = inscripcionService.joinOrDisjoin(id, nick, true);
		if(succes) {
			log.info("Succesfully join");
			return ResponseEntity.ok("Succesfully join");
		}else {
			return new ResponseEntity<>("Inscription not found or join not allowed", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/disjoin/{id}/{nick}") //alumno
	public ResponseEntity<?> disjoin(@PathVariable("id") Integer id, @PathVariable("nick") String nick){
		Boolean succes = inscripcionService.joinOrDisjoin(id, nick, false);
		if(succes) {
			log.info("Succesfully disjoin");
			return ResponseEntity.ok("Succesfully disjoin");
		}else {
			return new ResponseEntity<>("Inscription not found or disjoin not allowed", HttpStatus.NOT_FOUND);
		}
	}

}
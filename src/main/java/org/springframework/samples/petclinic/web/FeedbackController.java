package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.FeedbackService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	
	private FeedbackService feedbackService;

	@Autowired
	public FeedbackController(FeedbackService feedbackService) {
		super();
		this.feedbackService = feedbackService;
	};
	
	@PutMapping("/{idMaterial}/añadirAlumno")
	public ResponseEntity<?> añadirAlumnoAMaterial(@PathVariable("idMaterial") Integer idMaterial, @Valid @RequestBody Alumno alumno){
		log.info("he entrado en añadirAlumno");

		feedbackService.añadirAlumnoAMaterial(idMaterial,alumno);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/deleteMaterial/{idMaterial}")
	public ResponseEntity<?> deleteMaterial(@PathVariable("idMaterial") Integer idMaterial) throws IOException{
		log.info("Material con id:" + idMaterial);
		feedbackService.deleteMaterial(idMaterial);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/obtenerFeedback/{idMaterial}")
	public ResponseEntity<?> getFeedbackMaterial(@PathVariable("idMaterial")Integer idMaterial){
		return ResponseEntity.ok(feedbackService.getFeedbackByMaterial(idMaterial));
	}
	
	@PutMapping("/cambiarDone/{idFeedback}")
	public ResponseEntity<?> cambiarDone(@PathVariable("idFeedback")Integer idFeedback){
		feedbackService.cambiarEstadoDoneActividad(idFeedback);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{nickUser}/{idMaterial}")
	public ResponseEntity<?>getFeedback(@PathVariable("nickUser")String nickUser,@PathVariable("idMaterial")Integer idMaterial){
		return ResponseEntity.ok(feedbackService.getFeedbackByMaterialAndStudent(nickUser,idMaterial));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateFeedback(@RequestParam(value="comment",required=false)String comment, @RequestParam(value="rate",required=false)Integer rate, @RequestParam("id")Integer id){
		feedbackService.updateFeedback(comment, rate, id);
		return ResponseEntity.ok().build();
	}
}

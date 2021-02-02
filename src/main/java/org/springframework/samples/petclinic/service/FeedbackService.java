package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Feedback;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

	private MaterialService materialService;
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	public FeedbackService(MaterialService materialService, FeedbackRepository feedbackRepository) {
		super();
		this.materialService = materialService;
		this.feedbackRepository = feedbackRepository;
	}

	
	public void añadirAlumnoAMaterial(Integer idMaterial, @Valid Alumno alumno) {
		Material m = materialService.findMaterialById(idMaterial);
		Feedback f = new Feedback();
		f.setAlumnos(alumno);
		f.setCompletado(false);
		f.setMateriales(m);
		
		feedbackRepository.save(f);
	}
	
	public void deleteMaterial(Integer idMaterial) throws IOException {
		Material m = materialService.findMaterialById(idMaterial);
		List<Feedback> feedbacks = feedbackRepository.findFeedbacksByMaterial(m);
		for(Feedback f: feedbacks) {
			feedbackRepository.delete(f);
		}
		materialService.delete(m);
		
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		  	 
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + idMaterial + ".pdf");
	    Files.delete(rutaCompleta); 
	}
	
	public List<Feedback>getFeedbackByMaterial(Integer idMaterial){
		return feedbackRepository.findFeedbackByMaterial(idMaterial);
		
	}


	public void cambiarEstadoDoneActividad(Integer idFeedback) {
		Feedback f = feedbackRepository.findById(idFeedback).get();
		f.setCompletado(!f.getCompletado());
		if(f.getCompletado()) {
			f.setDiaEntrega(LocalDate.now());
		}else {
			f.setDiaEntrega(null);
		}
		feedbackRepository.save(f);
	}
	
}

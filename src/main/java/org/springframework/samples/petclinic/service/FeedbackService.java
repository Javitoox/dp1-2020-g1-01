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
	private AlumnoService alumnoService;
	
	@Autowired
	public FeedbackService(MaterialService materialService, FeedbackRepository feedbackRepository, AlumnoService alumnoService) {
		super(); 
		this.materialService = materialService;
		this.feedbackRepository = feedbackRepository;
		this.alumnoService= alumnoService;
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
		List<Feedback> feedbacks = feedbackRepository.findFeedbackByMaterial(idMaterial);
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
		Alumno a = f.getAlumnos();
		f.setCompletado(!f.getCompletado());
		if(f.getCompletado()) {
			f.setDiaEntrega(LocalDate.now());
			a.setNumTareasEntregadas(a.getNumTareasEntregadas()+1);
		}else {
			f.setDiaEntrega(null);
			a.setNumTareasEntregadas(a.getNumTareasEntregadas()-1);
		}
		alumnoService.saveAlumno(a);
		f.setAlumnos(a);
		feedbackRepository.save(f);
	}


	public Feedback getFeedbackByMaterialAndStudent(String nickUser, Integer idMaterial) {
		Alumno a = alumnoService.getAlumno(nickUser);
		Material m = materialService.findMaterialById(idMaterial);
		Feedback f = feedbackRepository.getFeedbackByMaterialAndStudent(a,m);
		return f;
	}


	public void updateFeedback(String comment, Integer rate, Integer id) {
		Feedback f = feedbackRepository.findById(id).get();
		f.setComentario(comment);
		f.setValoracion(rate);
		feedbackRepository.save(f);
	}

	
}

package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.TutorService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/requests")
public class SolicitudController {
	private static final Logger log = LoggerFactory.logger(SolicitudController.class);

	   private final SolicitudService solicitudServ;
	   private final AlumnoService alumnoService;
	   private final TutorService tutorService;
	   
	   @Autowired
	   public SolicitudController(SolicitudService solicitudServ, AlumnoService alumnoService, TutorService tutorService) {
		   this.solicitudServ = solicitudServ;
		   this.alumnoService = alumnoService;
		   this.tutorService = tutorService;
	   }
	   
	   @GetMapping("/pending")
	   public List<Alumno> getSolicitudes() {
		   return solicitudServ.getAllSolicitudes();
	   }
	   
	   @GetMapping("/decline/{nickUsuario}")
	   public void declineRequest(@PathVariable("nickUsuario")String nickUsuario ){
		   Alumno alumnoAceptado = alumnoService.getAlumno(nickUsuario);
		   if(alumnoAceptado.getTutores()==null) {
			   solicitudServ.declineRequest(nickUsuario);
		   }else {
			   String nickTutor = alumnoAceptado.getTutores().getNickUsuario();
			   List<Alumno> alumnosDelTutor=  alumnoService.getAllMyStudents(nickTutor);
			   if(alumnosDelTutor.size()>1) {
				   solicitudServ.declineRequest(nickUsuario);
			   }else {
				   solicitudServ.declineRequest(nickUsuario);
				   tutorService.delete(nickTutor);
				   
			   }
		   }
		   
	   }
	   
	   @GetMapping("/accept/{nickUsuario}")
	   public void sending(@PathVariable("nickUsuario")String nickUsuario) {
		   Alumno alumnoAceptado = alumnoService.getAlumno(nickUsuario);
		   System.out.println("ALUMNO ACEPTADO:"+alumnoAceptado);
		   alumnoAceptado.setFechaSolicitud(null);
		   alumnoAceptado.setFechaMatriculacion(null);
		   solicitudServ.acceptRequest(alumnoAceptado);
	   }   
		
		@GetMapping("/sending")
		public void sending(@Valid Solicitud solicitud, BindingResult result) {
			log.info(solicitud.getAlumno());
			solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
			solicitudServ.saveAlumno(solicitud.getAlumno());
		}

		@GetMapping("/sendingAll")
		public void sendingAll(@Valid Solicitud solicitud) {
			solicitud.getAlumno().setTutores(solicitud.getTutor());
			solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
			Tutor t2 = solicitudServ.getTutor(solicitud.getTutor().getNickUsuario());
			solicitudServ.updateRequestTutor(t2, solicitud.getTutor());
			solicitudServ.saveAlumno(solicitud.getAlumno());
			solicitudServ.saveTutor(solicitud.getTutor());
		}
	  
}

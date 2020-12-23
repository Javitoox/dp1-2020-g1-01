package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		
		@PostMapping("/sending")
		public ResponseEntity<?> sending(@Valid @RequestBody Solicitud solicitud, BindingResult result) {
			if(result.hasErrors()) {
				return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}else {
				Alumno alumno = solicitudServ.getAlumno(solicitud.getAlumno().getNickUsuario());
				if(alumno == null) {
					solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
					solicitudServ.saveRequest(solicitud);
					log.info("New student´s request with username: "+solicitud.getAlumno().getNickUsuario());
					return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
				}else if(alumno != null && alumno.getContraseya().equals(solicitud.getAlumno().getContraseya())){
					solicitudServ.saveRequest(solicitud);
					log.info("Update student´s request with username: "+solicitud.getAlumno().getNickUsuario());
					return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
				}else {
					log.info("Access denied: "+solicitud.getAlumno().getNickUsuario());
					return new ResponseEntity<>("The student already exists and his password is incorrect", 
							HttpStatus.OK);
				}
			}
		}

		@PostMapping("/sendingAll")
		public ResponseEntity<?> sendingAll(@Valid @RequestBody Solicitud solicitud, BindingResult result) {
			if(result.hasErrors()) {
				return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}else {
				Alumno alumno = solicitudServ.getAlumno(solicitud.getAlumno().getNickUsuario());
				Tutor tutor = solicitudServ.getTutor(solicitud.getTutor().getNickUsuario());
				if(alumno == null && tutor == null) {
					solicitud.getAlumno().setTutores(solicitud.getTutor());
					solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
					solicitud.getTutor().setFechaSolicitud(LocalDate.now());
					solicitudServ.saveRequest(solicitud);
					log.info("New student´s request with username: "+solicitud.getAlumno().getNickUsuario());
					log.info("New tutor´s request with username: "+solicitud.getTutor().getNickUsuario());
					return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
				}else if(alumno != null && alumno.getContraseya().equals(solicitud.getAlumno().getContraseya()) && tutor == null) {
					solicitud.getAlumno().setTutores(solicitud.getTutor());
					solicitud.getTutor().setFechaSolicitud(LocalDate.now());
					solicitudServ.saveRequest(solicitud);
					log.info("Update student´s request with username: "+solicitud.getAlumno().getNickUsuario());
					log.info("New tutor´s request with username: "+solicitud.getTutor().getNickUsuario());
					return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
				}else if(alumno == null && tutor != null && tutor.getContraseya().equals(solicitud.getTutor().getContraseya())) {
					solicitud.getAlumno().setTutores(solicitud.getTutor());
					solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
					solicitudServ.saveRequest(solicitud);
					log.info("New student´s request with username: "+solicitud.getAlumno().getNickUsuario());
					log.info("Update tutor´s request with username: "+solicitud.getTutor().getNickUsuario());
					return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
				}else if(alumno != null && alumno.getContraseya().equals(solicitud.getAlumno().getContraseya()) && 
						tutor != null && tutor.getContraseya().equals(solicitud.getTutor().getContraseya())) {
					solicitud.getAlumno().setTutores(solicitud.getTutor());
					solicitudServ.saveRequest(solicitud);
					log.info("Update student´s request with username: "+solicitud.getAlumno().getNickUsuario());
					log.info("Update tutor´s request with username: "+solicitud.getTutor().getNickUsuario());
					return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
				}else if(alumno != null && !alumno.getContraseya().equals(solicitud.getAlumno().getContraseya()) 
						&& tutor != null && !tutor.getContraseya().equals(solicitud.getTutor().getContraseya())) {
					log.info("Access denied: "+solicitud.getAlumno().getNickUsuario()+" and "+solicitud.getTutor().getNickUsuario());
					return new ResponseEntity<>("Student and tutor already exist and their passwords are incorrect", 
							HttpStatus.OK);
				}else if(alumno != null && !alumno.getContraseya().equals(solicitud.getAlumno().getContraseya())) {
					log.info("Access denied: "+solicitud.getAlumno().getNickUsuario());
					return new ResponseEntity<>("The student already exists and his password is incorrect", 
							HttpStatus.OK);
				}else{
					log.info("Access denied: "+solicitud.getTutor().getNickUsuario());
					return new ResponseEntity<>("The tutor already exists and his password is incorrect", 
							HttpStatus.OK);
				}
			}
		}
	  
}

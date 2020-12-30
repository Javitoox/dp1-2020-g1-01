package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/requests")
@Slf4j
public class SolicitudController {

	   private final SolicitudService solicitudServ;
	   
	   @Autowired
	   public SolicitudController(SolicitudService solicitudServ, AlumnoService alumnoService, TutorService tutorService) {
		   this.solicitudServ = solicitudServ;
	   }
	   
	   @GetMapping("/pending")
	   public ResponseEntity<?> getSolicitudes(HttpServletRequest request) {
		   HttpSession session = request.getSession(false);

		   log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		   if(session != null && session.getAttribute("type") == "profesor") {
			   return ResponseEntity.ok(solicitudServ.getAllSolicitudes());
		   }else {
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		   }
	   }
	   
	   @PutMapping("/decline/{nickUsuario}")
	   public ResponseEntity<?> declineRequest(@PathVariable("nickUsuario")String nickUsuario, HttpServletRequest request){
		   HttpSession session = request.getSession(false);

		   log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		   if(session != null && session.getAttribute("type") == "profesor") {
			   Alumno alumnoDenegado = solicitudServ.getAlumno(nickUsuario);
			   solicitudServ.declineRequest(alumnoDenegado);
			   return ResponseEntity.ok().build();
		   }else {
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		   }
		   
	   }
	   
	   @PutMapping("/accept/{nickUsuario}")
	   public ResponseEntity<?> sending(@PathVariable("nickUsuario")String nickUsuario, HttpServletRequest request) {
		   HttpSession session = request.getSession(false);
		   log.info("SESION: " + session);

		   log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		   if(session != null && session.getAttribute("type") == "profesor") {
			   Alumno alumnoAceptado = solicitudServ.getAlumno(nickUsuario);
			   System.out.println("ALUMNO ACEPTADO:"+alumnoAceptado);
			   alumnoAceptado.setFechaMatriculacion(LocalDate.now());
			   Tutor t = alumnoAceptado.getTutores();
			   if(t != null && t.getFechaMatriculacion() == null) {
				   t.setFechaMatriculacion(LocalDate.now());
				   alumnoAceptado.setTutores(t);
			   }
			   solicitudServ.acceptRequest(alumnoAceptado);
			   return ResponseEntity.ok().build();

		   }else {
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		   }
		   
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

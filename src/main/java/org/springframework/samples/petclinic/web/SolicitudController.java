package org.springframework.samples.petclinic.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/requests")
public class SolicitudController {
	private static final Logger log = LoggerFactory.logger(SolicitudController.class);
	
	   @Autowired
	   SolicitudService solicitudServ;
	   
	   @Autowired
	   AlumnoService alumnoService;
	   
	   @Autowired
	   TutorService tutorService;
	   
	   @GetMapping("/all")
	   public Collection<Solicitud> getSolicitudes(ModelMap model) {
		   return solicitudServ.getAllSolicitudes();
	   }
	   
//	   @GetMapping("/sending")
//	   public void sending(Alumno alumno, @RequestParam("nickUsuarioTutor") String nickUsuarioTutor, 
//			   @RequestParam("contraseyaTutor") String contraseyaTutor, @RequestParam("dniUsuarioTutor") String dniUsuarioTutor, 
//			   @RequestParam("nombreCompletoUsuarioTutor") String nombreCompletoUsuarioTutor, @RequestParam("correoElectronicoUsuarioTutor") 
//	           String correoElectronicoUsuarioTutor, @RequestParam("numTelefonoUsuarioTutor") String numTelefonoUsuarioTutor,  
//	           @RequestParam("direccionUsuarioTutor") String direccionUsuarioTutor, @RequestParam("fechaNacimientoTutor") String fechaNacimientoTutor) {
//		   alumnoService.insert(alumno);
//		   tutorService.insertParams(nickUsuarioTutor, contraseyaTutor, dniUsuarioTutor, nombreCompletoUsuarioTutor, correoElectronicoUsuarioTutor, 
//				   numTelefonoUsuarioTutor, direccionUsuarioTutor, fechaNacimientoTutor);
//	   }
	   
	   @GetMapping("/sendingAll")
	   public void sendingAll(@Valid Alumno alumno, BindingResult result1, @Valid Tutor tutor, BindingResult result2, 
			   HttpServletResponse response) throws IOException {
		   if(result1.hasErrors() || result2.hasErrors()) {
			   response.sendRedirect("http://localhost:3000/requests");
		   }else {
			   tutorService.insert(tutor);
			   alumnoService.saveAlumno(alumno);
			   solicitudServ.insertByNick(alumno.getNickUsuario());
			   solicitudServ.insertByNick(tutor.getNickUsuarioTutor());
			   response.sendRedirect("http://localhost:3000");
		   }
	   }
	   
	   @GetMapping("/sending")
	   public void sending(@Valid Alumno alumno, BindingResult result, HttpServletResponse response) throws IOException {
		   if(result.hasErrors()) {
			   response.sendRedirect("http://localhost:3000/requests");
		   }else {
			   alumnoService.saveAlumno(alumno);
			   solicitudServ.insertByNick(alumno.getNickUsuario());
			   response.sendRedirect("http://localhost:3000");
		   }
	   }
	   
}

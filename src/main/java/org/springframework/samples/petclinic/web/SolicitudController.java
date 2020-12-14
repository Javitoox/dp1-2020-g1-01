package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.service.TutorService;
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
	
	   @Autowired
	   SolicitudService solicitudServ;
	   
	   @Autowired
	   AlumnoService alumnoService;
	   
	   @Autowired
	   TutorService tutorService;
	   
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
	   
		/*
		 * @GetMapping("/sending") public void sending(@Valid Alumno alumno) {
		 * alumno.setFechaSolicitud("sysdate"); alumnoService.saveAlumno(alumno); }
		 * 
		 * @GetMapping("/sendingAll") public void sendingAll(@Valid Alumno
		 * alumno, @Valid Tutor tutor){ alumno.setTutores(tutor);
		 * alumno.setFechaSolicitud("sysdate"); Tutor t2 =
		 * tutorService.getTutor(tutor.getNickUsuarioTutor()); if(t2==null) {
		 * tutor.setFechaMatriculacionTutor("sysdate"); tutorService.insert(tutor); }
		 * alumnoService.saveAlumno(alumno); }
		 */
	   
	   /*
	   @GetMapping("/all")
	   public Collection<Solicitud> getSolicitudes(ModelMap model) {
		   return solicitudServ.getAllSolicitudes();
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
	   }*/
	   
}

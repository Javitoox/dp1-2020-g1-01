package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	   public void accept(@PathVariable("nickUsuario")String nickUsuario) {
		   Alumno alumnoAceptado = alumnoService.getAlumno(nickUsuario);
		   System.out.println("ALUMNO ACEPTADO:"+alumnoAceptado);
		   alumnoAceptado.setFechaMatriculacion(LocalDate.now());
		   solicitudServ.acceptRequest(alumnoAceptado);
	   }   
		
//		@GetMapping("/sending")
//		public void sending(@Valid Solicitud solicitud, BindingResult result) {
//			log.info(solicitud.getAlumno());
//			solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
//			solicitudServ.saveAlumno(solicitud.getAlumno());
//		}
//
//		@GetMapping("/sendingAll")
//		public void sendingAll(@Valid Solicitud solicitud) {
//			solicitud.getAlumno().setTutores(solicitud.getTutor());
//			solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
//			Tutor t2 = solicitudServ.getTutor(solicitud.getTutor().getNickUsuario());
//			solicitudServ.updateRequestTutor(t2, solicitud.getTutor());
//			solicitudServ.saveAlumno(solicitud.getAlumno());
//			solicitudServ.saveTutor(solicitud.getTutor());
//		}
//	  
		
	   /*
	    * 
	    * NO ESTA ACABADO, IN PROGRESS...
	    * 
	    * 
	    */
		
		@GetMapping("/sendingWithTutor")
		public void sending(@RequestParam("nickUsuario")String nickUsuario,@RequestParam("contraseya")String contraseya,@RequestParam("dniUsuario")String dniUsuario,@RequestParam("nombreCompletoUsuario")String nombreCompletoUsuario,@RequestParam("correoElectronicoUsuario")String correoElectronicoUsuario,@RequestParam("numTelefonoUsuario")String numTelefonoUsuario,@RequestParam("numTelefonoUsuario2")String numTelefonoUsuario2,@RequestParam("direccionUsuario")String direccionUsuario,@RequestParam("fechaNacimiento")String fechaNacimiento, 
				@RequestParam("nickUsuarioTutor")String nickUsuarioTutor,@RequestParam("contraseyaTutor")String contraseyaTutor,@RequestParam("dniUsuarioTutor")String dniUsuarioTutor,@RequestParam("nombreCompletoUsuarioTutor")String nombreCompletoUsuarioTutor,@RequestParam("correoElectronicoUsuarioTutor")String correoElectronicoUsuarioTutor,@RequestParam("numTelefonoUsuarioTutor")String numTelefonoUsuarioTutor,@RequestParam("numTelefonoUsuarioTutor2")String numTelefonoUsuarioTutor2,@RequestParam("direccionUsuarioTutor")String direccionUsuarioTutor,@RequestParam("fechaNacimientoTutor")String fechaNacimientoTutor) {
			System.out.println(nickUsuarioTutor);
		}
		
		@GetMapping("/sendingWithoutTutor")
		public ResponseEntity<Alumno> sendingAll(@RequestParam("nickUsuario")String nickUsuario,@RequestParam("contraseya")String contraseya,@RequestParam("dniUsuario")String dniUsuario,@RequestParam("nombreCompletoUsuario")String nombreCompletoUsuario,@RequestParam("correoElectronicoUsuario")String correoElectronicoUsuario,@RequestParam("numTelefonoUsuario")String numTelefonoUsuario,@RequestParam("numTelefonoUsuario2")String numTelefonoUsuario2,@RequestParam("direccionUsuario")String direccionUsuario,@RequestParam("fechaNacimiento")String fechaNacimiento) {
			Alumno alumnoAInsertar = new Alumno();
			alumnoAInsertar.setNickUsuario(nickUsuario);
			alumnoAInsertar.setContraseya(contraseya);
			alumnoAInsertar.setDniUsuario(dniUsuario);
			alumnoAInsertar.setCorreoElectronicoUsuario(correoElectronicoUsuario);
			alumnoAInsertar.setDireccionUsuario(direccionUsuario);
			alumnoAInsertar.setFechaBaja(null);
			alumnoAInsertar.setFechaMatriculacion(null);
			alumnoAInsertar.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
			alumnoAInsertar.setFechaSolicitud(LocalDate.now());
			alumnoAInsertar.setNombreCompletoUsuario(nombreCompletoUsuario);
			alumnoAInsertar.setNumTareasEntregadas(0);
			alumnoAInsertar.setNumTelefonoUsuario(numTelefonoUsuario);
			alumnoAInsertar.setNumTelefonoUsuario2(numTelefonoUsuario2);
			System.out.println(alumnoAInsertar.getNombreCompletoUsuario());
			Alumno alumnoInsertado = alumnoService.saveAlumno(alumnoAInsertar);
			return ResponseEntity.ok(alumnoInsertado);
		}
		
		
		
}

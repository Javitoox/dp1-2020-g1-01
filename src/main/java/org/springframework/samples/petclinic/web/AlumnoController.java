package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/alumnos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@Slf4j
public class AlumnoController {


	private AlumnoService alumnoServ;

	@Autowired
	public AlumnoController(AlumnoService alumnoServ) {
		super();
		this.alumnoServ = alumnoServ;
	}
	
	@PutMapping("/editStudent")
	public ResponseEntity<?> processUpdateAlumnoForm(@Valid @RequestBody Alumno alumno, HttpServletRequest request,HttpServletResponse response , BindingResult result)
			throws IOException {
		HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("type") == "alumno" || session.getAttribute("type") == "profesor" ) {
    		if (result.hasErrors()) {
    			log.info("Esto no funciona");
    			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    		}
    		else {
    			log.info("Ha funcionado");
    			this.alumnoServ.saveAlumno(alumno);
    			return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
    			
    		}
    	}else {
    		log.info("Que no bro hahah");
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
	
    @GetMapping("/getStudentInfo/{nickUsuario}")
    public ResponseEntity<Alumno> getStudentInfo(@PathVariable("nickUsuario") String nick, 
    		HttpServletRequest request){
    		Alumno alumno = alumnoServ.getAlumno(nick);
            return ResponseEntity.ok(alumno);
    
    }

	@GetMapping("/all")
	public ResponseEntity<?> listAlumnos(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		if(session != null && session.getAttribute("type") == "profesor") {
			List<Alumno> allStudents = alumnoServ.getAllAlumnos();
			return ResponseEntity.ok(allStudents);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}

	@GetMapping("/getByCourse/{course}")
	public ResponseEntity<?> listStudentsByCourse(@PathVariable("course") TipoCurso cursoDeIngles, HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		log.info("Obteniendo alumnos del curso: "+cursoDeIngles);
		if(session != null && session.getAttribute("type") == "profesor") {	
				List<Alumno> allStudentsByCourse = alumnoServ.getStudentsByCourse(cursoDeIngles);
				return ResponseEntity.ok(allStudentsByCourse);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 

		}
	}

	@GetMapping("/{nombreGrupo}")
	public ResponseEntity<List<Alumno>> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		List<Alumno> studentsByGroup = alumnoServ.getStudentsPerGroup(nombreGrupo);
		return ResponseEntity.ok(studentsByGroup);
	}
	
	@GetMapping("/{nickTutor}/allMyStudents")
    public ResponseEntity<?>getStudentsByTutor(@PathVariable("nickTutor") String nickTutor, 
    		HttpServletRequest request){
    	HttpSession session = request.getSession(false);
		log.info("Has iniciado sesion como: "+ session.getAttribute("type")+ ", y con nick: "+session.getAttribute("nickUsuario"));
    	if(session != null && session.getAttribute("type") == "tutor" && session.getAttribute("nickUsuario")==nickTutor) {
    		List<Alumno>studentsByTutor = alumnoServ.getAllMyStudents(nickTutor);
            return ResponseEntity.ok(studentsByTutor);
    	}else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
	}

	@PutMapping("/assignStudent")
	public ResponseEntity<?> assignStudent(@Valid @RequestBody Alumno alumno, HttpServletRequest request,HttpServletResponse response , BindingResult result)
			throws IOException {
		HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("type") == "alumno" || session.getAttribute("type") == "profesor" ) {
    		if (result.hasErrors()) {
    			log.info("Esto no funciona");
    			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    		}
    		else {
    			log.info("Ha funcionado");
    			this.alumnoServ.saveAlumno(alumno);
    			return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
    			
    		}
    	}else {
    		log.info("Que no bro hahah");
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
	
	

}

package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
public class AlumnoController {

	private AlumnoService alumnoServ;

	@Autowired
	public AlumnoController(AlumnoService alumnoServ) {
		this.alumnoServ = alumnoServ;
	}
	
	@PutMapping("/editStudent") //alumno, profesor
	public ResponseEntity<?> processUpdateAlumnoForm(@Valid @RequestBody Alumno alumno, BindingResult result) {
		if (result.hasErrors()) {
			log.info("Esto no funciona");
			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		else {
			log.info("Ha funcionado");
			this.alumnoServ.saveAlumno(alumno);
			return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			
		}
    }
	
    @GetMapping("/getStudentInfo/{nickUsuario}") //profesor, alumno
    public ResponseEntity<Alumno> getStudentInfo(@PathVariable("nickUsuario") String nick){
		Alumno alumno = alumnoServ.getAlumno(nick);
	    return ResponseEntity.ok(alumno);
    }
 
	@GetMapping("/all") //profesor
	public ResponseEntity<?> listAlumnos() {
		List<Alumno> allStudents = alumnoServ.getAllAlumnos();
		return ResponseEntity.ok(allStudents);
	}

	@GetMapping("/getByCourse/{course}") //profesor
	public ResponseEntity<?> listStudentsByCourse(@PathVariable("course") TipoCurso cursoDeIngles) {		
		log.info("Obteniendo alumnos del curso: "+cursoDeIngles);
		List<Alumno> allStudentsByCourse = alumnoServ.getStudentsByCourse(cursoDeIngles);
		return ResponseEntity.ok(allStudentsByCourse);
	}

	@GetMapping("/{nombreGrupo}") //profesor
	public ResponseEntity<List<Alumno>> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		log.info("Obteniendo alumnos del curso: "+ nombreGrupo);
		List<Alumno> studentsByGroup = alumnoServ.getStudentsPerGroup(nombreGrupo);
		return ResponseEntity.ok(studentsByGroup);
	}
	
	@GetMapping("/{nickTutor}/allMyStudents") //tutor
    public ResponseEntity<?>getStudentsByTutor(@PathVariable("nickTutor") String nickTutor, Authentication authentication){
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
    	if(username.equals(nickTutor)) {
    		List<Alumno>studentsByTutor = alumnoServ.getAllMyStudents(nickTutor);
            return ResponseEntity.ok(studentsByTutor);
    	}else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
	}

	@PutMapping("/assignStudent") // profesor
	public ResponseEntity<?> assignStudent(@Valid @RequestBody Alumno alumno, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			log.info("Request to edit alumn's group: {} ", alumno.getGrupos());
			this.alumnoServ.saveAlumno(alumno);
			return new ResponseEntity<>("Successful edit", HttpStatus.CREATED);

		}
	}

}

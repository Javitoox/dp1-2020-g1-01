package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.service.PremiadoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private PremiadoService premiadoService;
	private GrupoService grupoService;

	@Autowired
	public AlumnoController(AlumnoService alumnoServ, GrupoService grupoService, PremiadoService premiadoService) {
		this.alumnoServ = alumnoServ;
		this.premiadoService = premiadoService;
		this.grupoService = grupoService;
	} 
	
	@PutMapping("/editStudent")
	public ResponseEntity<?> processUpdateAlumnoForm(@Valid @RequestBody Alumno alumno, BindingResult result, HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("type") == "alumno" || session.getAttribute("type") == "profesor" ) {
    		if (result.hasErrors()) {
    			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    		}
    		else {
    			this.alumnoServ.saveAlumno(alumno);
    			return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
    			
    		}
    	}else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
	
    @GetMapping("/getStudentInfo/{nickUsuario}")
    public ResponseEntity<Alumno> getStudentInfo(@PathVariable("nickUsuario") String nick, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		
		if(session != null && (session.getAttribute("type") == "profesor" || session.getAttribute("type") == "alumno")) {
			Alumno alumno = alumnoServ.getAlumno(nick);
		    return ResponseEntity.ok(alumno);
		 }else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		 }	
    }
 
	@GetMapping("/all")
	public ResponseEntity<?> listAlumnos(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session != null && session.getAttribute("type") == "profesor") {
			List<Alumno> allStudents = alumnoServ.getAllAlumnos();
			return ResponseEntity.ok(allStudents);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}

	@GetMapping("/ableToDelete")
	public ResponseEntity<?> listAlumnosToDelete(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
			List<String> allStudents = alumnoServ.getStudentsToDelete();
			return ResponseEntity.ok(allStudents);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/studentsWithNoGroups")
	public ResponseEntity<?> listAlumnosWithNoGroups(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
			List<String> allStudents = alumnoServ.getStudentsWithNoGroups();
			return ResponseEntity.ok(allStudents);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}

	@GetMapping("/getByCourse/{course}")
	public ResponseEntity<?> listStudentsByCourse(@PathVariable("course") TipoCurso cursoDeIngles, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		log.info("Obteniendo alumnos del curso: "+cursoDeIngles);
		if(session != null && session.getAttribute("type") == "profesor") {	
			List<Alumno> allStudentsByCourse = alumnoServ.getStudentsByCourse(cursoDeIngles);
			return ResponseEntity.ok(allStudentsByCourse);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 

		}
	}

	@GetMapping("/{nombreGrupo}")
	public ResponseEntity<List<Alumno>> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		log.info("Obteniendo alumnos del curso: "+ nombreGrupo);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
			List<Alumno> studentsByGroup = alumnoServ.getStudentsPerGroup(nombreGrupo);
			return ResponseEntity.ok(studentsByGroup);
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/{nickTutor}/allMyStudents")
    public ResponseEntity<?>getStudentsByTutor(@PathVariable("nickTutor") String nickTutor, HttpServletRequest request){
    	HttpSession session = request.getSession(false);

    	if(session != null && session.getAttribute("type").equals("tutor") && session.getAttribute("nickUsuario").equals(nickTutor)) {
    		log.info("Has iniciado sesion como: "+ session.getAttribute("type")+ ", y con nick: "+session.getAttribute("nickUsuario"));
    		List<Alumno>studentsByTutor = alumnoServ.getAllMyStudents(nickTutor);
            return ResponseEntity.ok(studentsByTutor);
    	}else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
	}

	@PutMapping("/assignStudent")
	public ResponseEntity<?> assignStudent(@Valid @RequestBody Alumno alumno, HttpServletRequest request,HttpServletResponse response , BindingResult result){
		HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("type") == "profesor" ) {
    		if (result.hasErrors()) {
    			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    		}
    		else {
    			if(alumno.getGrupos().getNombreGrupo() != null) {
	    			Integer numAlumnosGrupo = grupoService.numAlumnos(alumno.getGrupos().getNombreGrupo());
    			if(numAlumnosGrupo < 3 ) {
	        			this.alumnoServ.saveAlumno(alumno);
		    			return new ResponseEntity<>("Successful edit", HttpStatus.CREATED);
	    			}else {
		    			return new ResponseEntity<>("El grupo tiene más de 12 alumnos", HttpStatus.ALREADY_REPORTED); //ERROR 208
	    			}
    			}
    				this.alumnoServ.saveAlumno(alumno);
	    			return new ResponseEntity<>("Successful edit", HttpStatus.CREATED);    			
    			}
    		}
    	else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
	
	@DeleteMapping("/delete/{nickUsuario}")
	public ResponseEntity<?> deleteStudent(@PathVariable("nickUsuario") String nickUsuario, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			log.info("Solicitando borrar alumno: {}", nickUsuario);
			if(alumnoServ.getStudentsToDelete().contains(nickUsuario.toString())) {
				premiadoService.deleteAlumno(nickUsuario);
				return new ResponseEntity<>("Alumno eliminado correctamente", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("No se puede borrar el alumno porque tiene pagos pendientes", HttpStatus.BAD_REQUEST);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
			}
		}
	
	

}

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
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/alumnos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class AlumnoController {
	private static final Logger LOGGER = LoggerFactory.logger(SolicitudController.class);

	@Autowired
	AlumnoService alumnoServ;

	@PutMapping("/editStudent")
	public ResponseEntity<?> processUpdateAlumnoForm(@Valid @RequestBody Alumno alumno, HttpServletRequest request,  BindingResult result)
			throws IOException {
		HttpSession session = request.getSession(false);
    	if(session != null) {
    		LOGGER.info("El alumno es : " + alumno.getNickUsuario());
    		if (result.hasErrors()) {
    			LOGGER.info("Esto no funciona");
    			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    		}
    		else {
    			LOGGER.info("Ha funcionado");
    			this.alumnoServ.saveAlumno(alumno);
    			return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
    			
    		}
    	}else {
    		LOGGER.info("Que no bro hahah");
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
    @GetMapping("/getStudentInfo/{nickUsuario}")
    public ResponseEntity<Alumno> getStudentInfo(@PathVariable("nickUsuario") String nick, 
    		HttpServletRequest request){
    		LOGGER.info("El nick del usuario en cuestion es " + nick);
    		Alumno alumno = alumnoServ.getAlumno(nick);
    		LOGGER.info("Se esta devolviendo flama");
            return ResponseEntity.ok(alumno);
    
    }

	@GetMapping("/all")
	public ResponseEntity<List<Alumno>> listAlumnos() {
		List<Alumno> allStudents = alumnoServ.getAllAlumnos();
		return ResponseEntity.ok(allStudents);
	}

	@GetMapping("/getByCourse/{course}")
	public ResponseEntity<List<Alumno>> listStudentsByCourse(@PathVariable("course") String cursoDeIngles) {
		List<String> cursos = new ArrayList<String>();
		cursos.add("A1");
		cursos.add("A2");
		cursos.add("B1");
		cursos.add("B2");
		cursos.add("C1");
		cursos.add("C2");
		cursos.add("APRENDIZAJELIBRE");
		if (cursos.contains(cursoDeIngles)) {
			List<Alumno> allStudentsByCourse = alumnoServ.getStudentsByCourse(cursoDeIngles);
			return ResponseEntity.ok(allStudentsByCourse);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{nombreGrupo}")
	public ResponseEntity<List<Alumno>> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		List<Alumno> studentsByGroup = alumnoServ.getStudentsPerGroup(nombreGrupo);
		return ResponseEntity.ok(studentsByGroup);
	}

	// @GetMapping("/{nick_usuario}/edit/{nombreGrupo}")/*Aquí podemos usar el
	// servicio de grupo*/
	@PutMapping("/assignStudent")
	public ResponseEntity<?> assignStudent(@Valid Alumno alumno, BindingResult result, HttpServletResponse response) throws IOException
			 {
		if (result.hasErrors()) {
			LOGGER.info("Esto no funciona");
		}
		else {
			LOGGER.info("Ha funcionado");
			this.alumnoServ.saveAlumno(alumno);
			response.sendRedirect("http://localhost:3000");
		}
	    return ResponseEntity.ok().build();
    }
}

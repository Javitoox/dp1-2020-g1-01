package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/tutor/{nickTutor}")
public class TutorController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping("/allMyStudents")
    public ResponseEntity<?>getStudentsByTutor(@PathVariable("nickTutor") String nickTutor, 
    		HttpServletRequest request){
    	HttpSession session = request.getSession(false);
    	if(session != null && session.getAttribute("type") == "tutor" && session.getAttribute("nickUsuario")==nickTutor) {
    		List<Alumno>studentsByTutor = alumnoService.getAllMyStudents(nickTutor);
            return ResponseEntity.ok(studentsByTutor);
    	}else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
}
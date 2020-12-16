package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/tutor/{nickTutor}")
public class TutorController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping("/allMyStudents")
    public ResponseEntity<List<Alumno>>getStudentsByTutor(@PathVariable("nickTutor") String nickTutor){
        List<Alumno>studentsByTutor = alumnoService.getAllMyStudents(nickTutor);
        return ResponseEntity.ok(studentsByTutor);
    }
}
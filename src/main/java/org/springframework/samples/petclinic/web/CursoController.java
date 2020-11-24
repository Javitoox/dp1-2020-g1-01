package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.service.CursoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/course")
public class CursoController {
	
	private final static Logger LOGGER = Logger.getLogger("");

    @Autowired
    CursoService cursoService;

    @PostMapping("/new/{nombreCurso}")
    public Curso saveNewCourse(@RequestParam("nombrecurso")String nombreCurso) { 
    	if(nombreCurso.equals("A1") || nombreCurso.equals("A2") || nombreCurso.equals("B1") || nombreCurso.equals("B2") 
    			|| nombreCurso.equals("C1") || nombreCurso.equals("C2") || nombreCurso.equals("APRENDIZAJELIBRE")) {
    		Curso c = new Curso();
            c.setCursoDeIngles(nombreCurso);
            return cursoService.saveNewCourse(c);
    	}else {
    		return null;
    	}
    }
    
    @DeleteMapping("/{nombreCurso}/delete")
    public void deleteCourseById(@RequestParam("nombreCurso") String nombreCurso) {
    	Optional<Curso> c = cursoService.getCourseById(nombreCurso);
    	if(c.isPresent()) {
    		cursoService.deleteCourse(c.get());
    	}else {
    		LOGGER.log(Level.WARNING, "This course doesn't exists.");
    	}
    }
 
}
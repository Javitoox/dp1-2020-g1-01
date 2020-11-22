package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.CursoDeIngles;
import org.springframework.samples.petclinic.service.CursoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/course")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @PostMapping("/new/{nombreCurso}")
    public Curso saveNewCourse(@RequestParam("nombrecurso")String nombreCurso) { 
        Curso c = new Curso();
        c.setCursoDeIngles(CursoDeIngles.A1);//esta puesto A1 por predeterminado pero habria que traer el valor del formulario y crear el curso a partir de esos datos.
        return cursoService.saveNewCourse(c);

    }

}
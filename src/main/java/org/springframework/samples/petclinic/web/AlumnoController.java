package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
   @Autowired
   AlumnoService alumnoServ;
   
   @GetMapping
   public String getAlumnos(ModelMap model) {
	   model.addAttribute("alumnos",alumnoServ.getAlumnos());
	   return "alumnos/AlumnosList";
   }
   
   @GetMapping("/{id}/edit")
   public String sendFormAlumno(ModelMap model, @PathVariable("id")int id ) {
	  Optional<Alumno>alumno = alumnoServ.findById(id);
	  if(alumno.isPresent()) {
		   model.addAttribute("alumno",alumno.get());
		   return "alumnos/editForm";
	  }else {
		  model.addAttribute("mensaje","No hemos podido encontrar el alumno. Inténtelo de nuevo");
		  return getAlumnos(model);
	  }
   }
   
   @PostMapping("/{id}/edit")
   public String editAlumno(@PathVariable("id") int id,@Valid Alumno alumnoModificado, BindingResult binding, ModelMap model) {
       Optional<Alumno> alumno= alumnoServ.findById(id);
       if(binding.hasErrors()) {
           return "alumnos/editForm";
       }else {
           BeanUtils.copyProperties(alumnoModificado, alumno.get(), "id");
           alumnoServ.saveAlumno(alumno.get());
           model.addAttribute("message","Alumno actualizado correctamente");
           return getAlumnos(model);
       }
   }
   
   @GetMapping("/{id}/delete")
   public String deleteAlumno(ModelMap model, @PathVariable("id")int id ) {
	  Optional<Alumno>alumno = alumnoServ.findById(id);
	  if(alumno.isPresent()) {
		   alumnoServ.deleteAlumno(alumno.get());
           model.addAttribute("message","Alumno eliminado correctamente");
		   return getAlumnos(model);
	  }else {
		  model.addAttribute("mensaje","No hemos podido encontrar el alumno. Inténtelo de nuevo");
		  return getAlumnos(model);
	  }
   }

   
}

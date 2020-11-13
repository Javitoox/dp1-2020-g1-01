package org.springframework.samples.petclinic.web;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
   @Autowired
   AlumnoService alumnoServ;
   
   @GetMapping("/all")
   public Collection<Alumno> getAlumnos(ModelMap model) {
	   return alumnoServ.getAlumnos();
   }
/*   
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
*/
   
}
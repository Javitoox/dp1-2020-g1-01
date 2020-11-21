package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/alumnos")
@CrossOrigin("*")
@RestController
public class AlumnoController {
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.control");
	
	AlumnoService alumnoServ;
	
	 @GetMapping("/all")
	public Collection<Alumno> getAlumnos(ModelMap model) {
		   return alumnoServ.getAllAlumnos();
	   }
	
	 
	 @PostMapping(value = "/{nick_usuario}/edit")
		public void processUpdateAlumnoForm(@Valid Alumno alumno, BindingResult result,
				@PathVariable("nick_usuario") String nick_usuario) {
			if (result.hasErrors()) {
				LOGGER.log(Level.INFO, "Esto no funciona :(");
			}
			else {
				alumno.setNickUsuario(nick_usuario);
				this.alumnoServ.saveAlumno(alumno);
			}
		}

}

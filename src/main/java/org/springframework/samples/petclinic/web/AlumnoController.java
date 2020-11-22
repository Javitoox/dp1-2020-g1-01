package org.springframework.samples.petclinic.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

	/*
	 * @Autowired AlumnoService alumnoService;
	 * 
	 * @GetMapping(value="/alumnos?grupo={nombreGrupo}") public String
	 * getPersonasByNameOfGroup(@RequestParam("nombreGrupo") String nombreGrupo,
	 * ModelMap model){ model.addAttribute("alumnosPorGrupo",
	 * alumnoService.getStudentsPerGroup(nombreGrupo)); return "grupos/alumnos"; }
	 */
	
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.control");
	
	@Autowired
	AlumnoService alumnoServ;
	
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
	
	@PostMapping(value = "/{nick_usuario}/edit?grupo=nombreGrupo")
	public void processUpdateStudentGroup(@Valid Alumno alumno, BindingResult result,
			@PathVariable("nick_usuario") String nick_usuario, @RequestParam("nombreGrupo") String nombreGrupo) {
		if (result.hasErrors()) {
			LOGGER.log(Level.INFO, "Esto no funciona :(");
		}
		else {
			Curso curso= alumnoServ.getAlumno(nick_usuario).getGrupos().getCurso();
			Grupo grupo = new Grupo();
			grupo.setNombreGrupo(nombreGrupo);
			grupo.setCurso(curso);
			alumno.setGrupos(grupo);
			this.alumnoServ.saveAlumno(alumno);
		}
	}
	
 



}


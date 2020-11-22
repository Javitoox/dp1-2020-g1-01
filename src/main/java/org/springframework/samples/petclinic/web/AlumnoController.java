package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/alumnos")
@CrossOrigin("*")
@RestController
public class AlumnoController {
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
	 
	 @GetMapping("/all")
	    public List<Alumno> listAlumnos(){
	        return alumnoServ.getAllAlumnos().stream().filter(x->x.getGrupos()!=null).collect(Collectors.toList());
	    }

    @GetMapping("/getByCourse/{course}")
    	public List<Alumno> listStudentsByCourse(@PathVariable("course") String cursoDeIngles){
        	return alumnoServ.getStudentsByCourse(cursoDeIngles);
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

package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
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
	GrupoService grupoService;

	
	  @GetMapping("/editStudent")
		public void processUpdateAlumnoForm(@Valid Alumno alumno, BindingResult result, HttpServletResponse response) throws IOException
				 {
			if (result.hasErrors()) {
				LOGGER.log(Level.INFO, "Esto no funciona :(");
			}
			else {
				LOGGER.log(Level.INFO, "Ha funcionado ;)))");
				this.alumnoServ.saveAlumno(alumno);
				response.sendRedirect("http://localhost:3000");
			}
		}
	 
	 @GetMapping("/all")
	    public List<Alumno> listAlumnos(){
	        return alumnoServ.getAllAlumnos();
	    }

    @GetMapping("/getByCourse/{course}")
    	public List<Alumno> listStudentsByCourse(@PathVariable("course") String cursoDeIngles){
        	return alumnoServ.getStudentsByCourse(cursoDeIngles);
    }
    
    @GetMapping(value="/getByNameOfGroup/{nombreGrupo}")	
	public List<Alumno> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo){
        return alumnoServ.getStudentsPerGroup(nombreGrupo);
    }

    @GetMapping(value = "/{nick_usuario}/edit/{nombreGrupo}")
    public void processUpdateStudentGroup(@PathVariable("nick_usuario") String nick_usuario, @PathVariable("nombreGrupo") String nombreGrupo) {
        	Alumno alumno1= alumnoServ.findById(nick_usuario);
        	Grupo grupo= grupoService.getCourseById(nombreGrupo);       
            alumno1.setGrupos(grupo);
            this.alumnoServ.saveAlumno(alumno1);
    }
}

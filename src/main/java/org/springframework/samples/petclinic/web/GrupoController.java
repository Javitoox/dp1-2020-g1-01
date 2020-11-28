package org.springframework.samples.petclinic.web;

import java.io.IOException;
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
import org.springframework.samples.petclinic.service.CursoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/grupos")
public class GrupoController {
	

	//private static final String vista = "grupos/list";
	//private static final String VIEWS_GROUPS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private final GrupoService grupoService;
	private final AlumnoService alumnoService;
	private final CursoService cursoService;
	private final static Logger LOGGER = Logger.getLogger("");
	
	@Autowired
	public GrupoController(GrupoService grupoService, AlumnoService alumnoService, CursoService cursoService) {
		this.grupoService = grupoService;
		this.alumnoService = alumnoService;
		this.cursoService = cursoService;
	}
	
	@GetMapping("/all")
	public List<Grupo> listaGrupos() {
		return grupoService.findAll().stream().collect(Collectors.toList());
		
	}
	
	@GetMapping("/{curso}")
	public List<Grupo> listaGruposPorCurso(@PathVariable("curso") String curso) {
		return grupoService.getGrupos(curso);		
	}
	
	@PostMapping("/new/{curso}/{nombregrupo}")
	public Grupo newGroup(@RequestParam ("nombregrupo") String nombregrupo, @RequestParam("curso") String curso) {
		Grupo g = new Grupo();
		Curso c = cursoService.getCourseById(curso).get();
		g.setCursos(c);
		g.setNombreGrupo(nombregrupo);
		return grupoService.saveGroup(g);
	}
	
	@PutMapping("/editGroup")
	public void updateGroup(@Valid Grupo grupo, BindingResult result/*, HttpServletResponse response*/) throws IOException{
		
		if(result.hasErrors()) {
			LOGGER.log(Level.INFO, "Error al editar el grupo");
		}else {
			LOGGER.log(Level.INFO, "Cambios realizados correctamente");
			this.grupoService.saveGroup(grupo);
		}
	}
	
	@DeleteMapping("/{nombreGrupo}/delete")
	public void deleteGroup(@RequestParam("nombreGrupo") String nombreGrupo) throws IOException {
		Grupo g = grupoService.getGrupo(nombreGrupo).get();
		grupoService.deleteGroup(g);
		
	}	
	
	@GetMapping(value="/grupos?grupo={nombreGrupo}")	
	public List<Alumno> getPersonasByNameOfGroup(@RequestParam("nombreGrupo") String nombreGrupo){
	@GetMapping(value="/getByNameOfGroup/{nombreGrupo}")	
	public List<Alumno> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo){
        return alumnoService.getStudentsPerGroup(nombreGrupo);
    }
	
	@GetMapping(value = "/{nick_usuario}/edit/{nombreGrupo}")
    public void processUpdateStudentGroup(@PathVariable("nick_usuario") String nick_usuario, @PathVariable("nombreGrupo") String nombreGrupo) {
        	Alumno alumno1= alumnoService.findById(nick_usuario);
        	Grupo grupo= grupoService.getCourseById(nombreGrupo);       
            alumno1.setGrupos(grupo);
            this.alumnoService.saveAlumno(alumno1);
    }
	
	
	
	

}

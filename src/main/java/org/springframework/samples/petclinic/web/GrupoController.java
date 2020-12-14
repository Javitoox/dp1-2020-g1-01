package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.CursoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/grupos")
public class GrupoController {
	
	private final GrupoService grupoService;
	private final AlumnoService alumnoService;
	private final CursoService cursoService;
	//private final static Logger LOGGER = Logger.getLogger("");
	
	@Autowired
	public GrupoController(GrupoService grupoService, AlumnoService alumnoService, CursoService cursoService) {
		this.grupoService = grupoService;
		this.alumnoService = alumnoService;
		this.cursoService = cursoService;
	}
	
	@GetMapping("/all")
	public List<Grupo> listaGrupos() {
		return grupoService.findAll();
	}
	
	@GetMapping("/{curso}")
	public List<Grupo> listaGruposPorCurso(@PathVariable("curso") String curso) {
		return grupoService.getGrupos(curso);		
	}
	
	//@GetMapping("/new/{curso}/{nombregrupo}")
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public Grupo create(@RequestBody Grupo resource){
//		if(resource == null) {
//			throw new Exception();
//		}else {
			
//		}
		return grupoService.crearGrupo(resource);
	}
	public void newGroup(@PathVariable ("nombregrupo") String nombregrupo, @PathVariable("curso") String curso) {
		Grupo g = new Grupo();
		Curso c = cursoService.getCourseById(curso).get();
		g.setCursos(c);
		g.setNombreGrupo(nombregrupo);
		grupoService.saveGroup(g);
	}
	
	@GetMapping("/{nombreGrupo}/delete")
	public void deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo) throws IOException {
		Grupo g = grupoService.getGrupo(nombreGrupo).get();
		grupoService.deleteGroup(g);
		
	}	
	
	
	@GetMapping(value = "/{nick_usuario}/edit/{nombreGrupo}")
    public void processUpdateStudentGroup(@PathVariable("nick_usuario") String nick_usuario, @PathVariable("nombreGrupo") String nombreGrupo) {
        	Alumno alumno1= alumnoService.findById(nick_usuario);
        	Grupo grupo= grupoService.getCourseById(nombreGrupo);       
            alumno1.setGrupos(grupo);
            this.alumnoService.saveAlumno(alumno1);
    }
	
}

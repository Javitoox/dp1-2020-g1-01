package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@Autowired
	public GrupoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}
	
	@GetMapping("/all")
	public Set<Grupo> listaGrupos() {
		return grupoService.getAllGrupos();
	}
	
	@GetMapping("/{curso}")
	public List<Grupo> listaGruposPorCurso(@PathVariable("curso") String curso) {
		return grupoService.getGrupos(curso);		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Grupo create(@RequestBody Grupo resource){
		grupoService.saveGroup(resource);
		return resource;
	}
	
	//@GetMapping("/{nombreGrupo}/delete")
	@DeleteMapping("/{nombreGrupo}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo) throws IOException {
		grupoService.deleteGroup(nombreGrupo);
	}	
	
	/*Cambiar a UPDATE*/
	@GetMapping(value = "/{nick_usuario}/edit/{nombreGrupo}")
	@ResponseStatus(HttpStatus.OK)
    public void processUpdateStudentGroup(@PathVariable("nick_usuario") String nick_usuario, @PathVariable("nombreGrupo") String nombreGrupo) {
        	Alumno alumno1= grupoService.findAlumnById(nick_usuario);
        	Grupo grupo= grupoService.getGroupById(nombreGrupo);       
            alumno1.setGrupos(grupo);
            this.grupoService.saveAlumn(alumno1);
    }
	
}

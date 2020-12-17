package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.service.exceptions.BadRequestException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/grupos")
public class GrupoController {
	
	private final GrupoService grupoService;
	//private static final Logger log = LoggerFactory.getLogger(GrupoController.class);
	
	@Autowired
	public GrupoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<Set<Grupo>> listaGrupos() {
		Set<Grupo> all =  grupoService.getAllGrupos();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/{curso}")
	public ResponseEntity<List<Grupo>> listaGruposPorCurso(@PathVariable("curso") String curso) {
		List<Grupo> gruposCurso = grupoService.getGrupos(curso);	
		return ResponseEntity.ok(gruposCurso);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Grupo resource) throws DuplicatedGroupNameException{
		log.info("Request to create group: {}", resource);
		if(resource == null) {
			return new ResponseEntity<>("Los campos están vacíos", HttpStatus.BAD_REQUEST);
		}else {
			grupoService.crearGrupo(resource);
			return new ResponseEntity<>("Grupo creado correctamente", HttpStatus.CREATED);
		}
	}
	
	
	@DeleteMapping("/{nombreGrupo}")
	public ResponseEntity<?> deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo)throws BadRequestException{
		log.info("Solicitando borrar grupo: {}", nombreGrupo);
//		try {
			grupoService.deleteGroup(nombreGrupo);
//		} catch (BadRequestException e) {
//			return new ResponseEntity<>("No se puede borrar el grupo porque tiene alumnos", HttpStatus.BAD_REQUEST);
//		}
		return ResponseEntity.ok().build();
	}	

}

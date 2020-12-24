package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.service.exceptions.BadRequestException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.validation.BindingResult;
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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
		List<Grupo> gruposCurso = grupoService.getGruposByCourse(curso);	
		return ResponseEntity.ok(gruposCurso);
	}
	
	@GetMapping("/allGroupNames")
	public ResponseEntity<List<String>> listaNombreGrupos() {
		List<String> all =  grupoService.getGroupNames();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/allEmptyGroups")
	public ResponseEntity<List<String>> listaNombreGruposVacios() {
		List<String> all =  grupoService.getEmptyGroups();
		return ResponseEntity.ok(all);
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody Grupo resource, BindingResult result) throws DuplicatedGroupNameException{
		log.info("Solicitando crear grupo: {}", resource);
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}else {
			grupoService.saveGroup(resource);
			return new ResponseEntity<>("Grupo creado correctamente", HttpStatus.CREATED);
		}
	}
	

	@DeleteMapping("/delete/{nombreGrupo}")
	public ResponseEntity<?> deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo)throws BadRequestException{
		log.info("Solicitando borrar grupo: {}", nombreGrupo);
		grupoService.deleteGroup(nombreGrupo);
		return new ResponseEntity<>("Grupo eliminado correctamente", HttpStatus.OK);
	}	

}

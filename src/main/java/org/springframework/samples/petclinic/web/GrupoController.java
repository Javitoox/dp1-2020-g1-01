package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
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
	private final AlumnoService alumnoService;

	@Autowired
	public GrupoController(GrupoService grupoService, AlumnoService alumnoService) {
		this.grupoService = grupoService;
		this.alumnoService = alumnoService;
	}
	
	@GetMapping("/all") //profesor
	public ResponseEntity<Set<Grupo>> listaGrupos() {
		Set<Grupo> all =  grupoService.getAllGrupos();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/nombresGrupo/{curso}") //profesor
	public ResponseEntity<List<String>> listaNombreGruposPorCurso(@PathVariable("curso") TipoCurso curso) {
		List<String> gruposCurso = grupoService.getNameGruposByCourse(curso);	
		return ResponseEntity.ok(gruposCurso);
	}
	
	@GetMapping("/nombreCurso/{grupo}") //profesor
	public ResponseEntity<List<String>> listaNombreCursoPorGrupo(@PathVariable("grupo") String grupo) {
		List<String> nombreCurso = grupoService.getCursoByGrupo(grupo);	
		return ResponseEntity.ok(nombreCurso);
	}
	
	@GetMapping("/allGroupNames") //profesor
	public ResponseEntity<List<String>> listaNombreGrupos() {
		List<String> all =  grupoService.getGroupNames();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/allEmptyGroups") //profesor
	public ResponseEntity<List<String>> listaNombreGruposVacios() {
		List<String> all =  grupoService.getEmptyGroups();
		return ResponseEntity.ok(all);
	}
	
	@PostMapping("/new") //profesor
	public ResponseEntity<?> create(@Valid @RequestBody Grupo resource, BindingResult result){
		log.info("Solicitando crear grupo: {}", resource);
		if (result.hasErrors() || grupoService.exists(resource.getNombreGrupo())) {
			if (grupoService.exists(resource.getNombreGrupo())) {
				return new ResponseEntity<>("Grupo ya existente", HttpStatus.IM_USED);
			} else {
				return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}
		} else {
			grupoService.saveGroup(resource);
			return new ResponseEntity<>("Grupo creado correctamente", HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/delete/{nombreGrupo}") // profesor
	public ResponseEntity<?> deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		log.info("Solicitando borrar grupo: {}", nombreGrupo);
		if (alumnoService.getStudentsPerGroup(nombreGrupo).isEmpty()) {
			grupoService.deleteGroup(nombreGrupo);
			return new ResponseEntity<>("Grupo eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se puede borrar el grupo porque tiene alumnos", HttpStatus.BAD_REQUEST);
		}
	}

}

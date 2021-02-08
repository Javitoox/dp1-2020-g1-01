package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Grupo;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/grupos")
public class GrupoController {

	private final GrupoService grupoService;

	@Autowired
	public GrupoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	@GetMapping("/all")
	public ResponseEntity<Set<Grupo>> listaGrupos() {
		Set<Grupo> all = grupoService.getAllGrupos();
		return ResponseEntity.ok(all);
	}

	@GetMapping("/nombresGrupo/{curso}")
	public ResponseEntity<List<String>> listaNombreGruposPorCurso(@PathVariable("curso") String curso) {
		List<String> gruposCurso = grupoService.getNameGruposByCourse(curso);
		return ResponseEntity.ok(gruposCurso);
	}

	@GetMapping("/nombreCurso/{grupo}")
	public ResponseEntity<List<String>> listaNombreCursoPorGrupo(@PathVariable("grupo") String grupo) {
		List<String> nombreCurso = grupoService.getCursoByGrupo(grupo);
		return ResponseEntity.ok(nombreCurso);
	}

	@GetMapping("/allGroupNames")
	public ResponseEntity<List<String>> listaNombreGrupos() {
		List<String> all = grupoService.getGroupNames();
		return ResponseEntity.ok(all);
	}

	@GetMapping("/allAsignableGroups/{nickUsuario}")
	public ResponseEntity<List<String>> listaNombreGruposAsginablesPorAlumno(
			@PathVariable("nickUsuario") String nickUsuario) {
		List<String> all = grupoService.getAssignableGroupsByStudent(nickUsuario);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/allEmptyGroups")
	public ResponseEntity<List<String>> listaNombreGruposVacios() {
		List<String> all = grupoService.getEmptyGroups();
		return ResponseEntity.ok(all);
	}

	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody Grupo resource, BindingResult result) {
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

	@DeleteMapping("/delete/{nombreGrupo}")
	public ResponseEntity<?> deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		log.info("Solicitando borrar grupo: {}", nombreGrupo);
		if (grupoService.grupoVacio(nombreGrupo)) {
			grupoService.deleteGroup(nombreGrupo);
			return new ResponseEntity<>("Grupo eliminado correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se puede borrar el grupo porque tiene alumnos", HttpStatus.BAD_REQUEST);
		}
	}

}

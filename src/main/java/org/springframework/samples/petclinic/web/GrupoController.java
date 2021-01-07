package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
	
	@GetMapping("/all")
	public ResponseEntity<Set<Grupo>> listaGrupos(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			Set<Grupo> all =  grupoService.getAllGrupos();
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/nombresGrupo/{curso}")
	public ResponseEntity<List<String>> listaNombreGruposPorCurso(@PathVariable("curso") TipoCurso curso, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			List<String> gruposCurso = grupoService.getNameGruposByCourse(curso);	
			return ResponseEntity.ok(gruposCurso);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/nombreCurso/{grupo}")
	public ResponseEntity<List<String>> listaNombreCursoPorGrupo(@PathVariable("grupo") String grupo, HttpServletRequest request) {
		HttpSession session = request.getSession(false);		
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			List<String> nombreCurso = grupoService.getCursoByGrupo(grupo);	
			return ResponseEntity.ok(nombreCurso);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/allGroupNames")
	public ResponseEntity<List<String>> listaNombreGrupos(HttpServletRequest request) {
		HttpSession session = request.getSession(false);		
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			List<String> all =  grupoService.getGroupNames();
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/allEmptyGroups")
	public ResponseEntity<List<String>> listaNombreGruposVacios(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			List<String> all =  grupoService.getEmptyGroups();
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody Grupo resource, BindingResult result, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			log.info("Solicitando crear grupo: {}", resource);
			if(result.hasErrors()) {
				return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}else {				
				if(grupoService.exists(resource.getNombreGrupo())) {
					return new ResponseEntity<>("Grupo ya existente", HttpStatus.BAD_REQUEST);
				}else{
				
				grupoService.saveGroup(resource);
				return new ResponseEntity<>("Grupo creado correctamente", HttpStatus.CREATED);
				}
			}
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	

	@DeleteMapping("/delete/{nombreGrupo}")
	public ResponseEntity<?> deleteGroup(@PathVariable("nombreGrupo") String nombreGrupo, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			log.info("Solicitando borrar grupo: {}", nombreGrupo);
			if(alumnoService.getStudentsPerGroup(nombreGrupo).isEmpty()) {
				grupoService.deleteGroup(nombreGrupo);
				return new ResponseEntity<>("Grupo eliminado correctamente", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("No se puede borrar el grupo porque tiene alumnos", HttpStatus.BAD_REQUEST);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
			}
		}

}

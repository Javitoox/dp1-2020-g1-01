package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.service.AsignacionProfesorService;
import org.springframework.samples.petclinic.service.ProfesorService;
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
@RequestMapping("/asignaciones")
public class AsignacionesProfesorController {

	private AsignacionProfesorService asignacionS;
	private ProfesorService profesorS;

	@Autowired
	public AsignacionesProfesorController(AsignacionProfesorService asignacionS, ProfesorService profesorS) {
		this.asignacionS = asignacionS;
		this.profesorS = profesorS;
	}

	@GetMapping("/get/{user}")
	public ResponseEntity<List<AsignacionProfesor>> listaAsignaciones(@PathVariable("user") String user) {
		List<AsignacionProfesor> all = asignacionS.getAllAsignacionesByUser(user);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/{nombreGrupo}")
	public ResponseEntity<Profesor> profesorByGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		List<String> all = asignacionS.findAsignacionesByGroup(nombreGrupo);
		Profesor p = profesorS.getProfesor(all.get(0));
		return ResponseEntity.ok(p);
	}

	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody AsignacionProfesor resource, BindingResult result) {
		log.info("Solicitando asignar profesor: {}", resource);
		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			if (resource.getGrupo().getNombreGrupo() == "" || resource.getGrupo().getNombreGrupo() == null) {
				log.info("Incorrect name of group:" + resource.getGrupo().getNombreGrupo());

				return new ResponseEntity<>("Name of group incorrect", HttpStatus.OK);

			} else {
				asignacionS.saveAsignacion(resource);
				return new ResponseEntity<>("Grupo creado correctamente", HttpStatus.CREATED);
			}
		}
	}

	@DeleteMapping("/delete/{nickProfesor}/{nombreGrupo}")
	public ResponseEntity<?> deleteGroup(@PathVariable("nickProfesor") String resource,
			@PathVariable("nombreGrupo") String resource2) {
		AsignacionProfesorKey a = new AsignacionProfesorKey();
		a.setNickProfesor(resource);
		a.setNombreGrupo(resource2);
		log.info("Request:" + resource);
		log.info("Solicitando borrar asignacion: {}", resource);
		asignacionS.deleteAsignacion(a);
		return new ResponseEntity<>("Asignacion eliminada correctamente", HttpStatus.OK);
	}

}

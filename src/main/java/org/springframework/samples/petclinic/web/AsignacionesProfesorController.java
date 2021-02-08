package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;
import org.springframework.samples.petclinic.service.AsignacionProfesorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

	@Autowired
	public AsignacionesProfesorController(AsignacionProfesorService asignacionS) {
		this.asignacionS = asignacionS;
	}

	@GetMapping("/get/{user}")
	public ResponseEntity<List<AsignacionProfesor>> listaAsignaciones(@PathVariable("user") String user,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getUsername().equals(user)) {
			List<AsignacionProfesor> all = asignacionS.getAllAsignacionesByUser(user);
			return ResponseEntity.ok(all);
		} else {
			log.warn("El usuario no coincide con el usuario logueado");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/getNick/{nombreGrupo}")
	public ResponseEntity<List<String>> profesorByGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		List<String> all = asignacionS.findAsignacionesByGroup(nombreGrupo);
		return ResponseEntity.ok(all);
	}

	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody AsignacionProfesor resource, BindingResult result) {
		log.info("Solicitando asignar profesor: {}", resource);
		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			if (resource.getGrupo().getNombreGrupo() == "" || resource.getGrupo().getNombreGrupo() == null) {
				log.info("Incorrect name of group:" + resource.getGrupo().getNombreGrupo());

				return new ResponseEntity<>("Name of group incorrect", HttpStatus.IM_USED);

			} else {
				asignacionS.saveAsignacion(resource);
				return new ResponseEntity<>("Asignacion realizada correctamente", HttpStatus.CREATED);
			}
		}
	}

	@DeleteMapping("/delete/{nickProfesor}/{nombreGrupo}")
	public ResponseEntity<?> deleteGroup(@PathVariable("nickProfesor") String nickProfesor,
			@PathVariable("nombreGrupo") String nombreGrupo, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getUsername().equals(nickProfesor)) {
			AsignacionProfesorKey a = new AsignacionProfesorKey();
			a.setNickProfesor(nickProfesor);
			a.setNombreGrupo(nombreGrupo);
			log.info("Solicitante:" + nickProfesor);
			log.info("Solicitando borrar asignacion del grupo: {}", nombreGrupo);
			asignacionS.deleteAsignacion(a);
			return new ResponseEntity<>("Asignacion eliminada correctamente", HttpStatus.OK);
		} else {
			log.warn("No se puede borrar una asignacion de un grupo que no perteneces al usuario logueado");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}

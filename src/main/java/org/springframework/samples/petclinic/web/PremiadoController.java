package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.BodyPremiado;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PremiadoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/premiados")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
public class PremiadoController {

	private PremiadoService premiadoService;

	private AlumnoService alumnoService;

	@Autowired
	public PremiadoController(PremiadoService premiadoService, AlumnoService alumnoService) {
		this.premiadoService = premiadoService;
		this.alumnoService = alumnoService;
	}

	@GetMapping("/{fechaWall}")
	public ResponseEntity<?> premiadosPorFecha(@PathVariable(name = "fechaWall") String fechaWall) {
		List<Premiado> premiados = premiadoService.premiadosPorFecha(fechaWall);
		return ResponseEntity.ok(premiados);
	}

	@GetMapping("/ultimaSemana")
	public ResponseEntity<?> obtenerUltimaSemana() {
		return ResponseEntity.ok(premiadoService.obtenerUltimaSemana());
	}

	@PostMapping(value = "/anadirPremiado/{fechaWall}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> añadirPremiado(@PathVariable("fechaWall") String fechaWall,
			@Valid @ModelAttribute BodyPremiado body, BindingResult result) throws DataAccessException, IOException {
		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		String nickUsuario = body.getNickUsuario();
		String description = body.getDescription();
		MultipartFile file = body.getPhoto();
 
		Alumno alumno = alumnoService.getAlumno(nickUsuario);
		if (alumno != null) {
			Integer numApariciones = premiadoService.numAparicionesEnFecha(fechaWall, nickUsuario);
			log.info("El alumno con nick " + nickUsuario + " aparece " + numApariciones + " vez(veces");
			if (numApariciones < 1) {
				premiadoService.insertarPremiado(alumno, fechaWall, description, file);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/editarPremiado", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> editarPremiado(@RequestParam(value = "photo", required = false) MultipartFile file,
			@RequestParam("id") Integer id, @RequestParam("description") String descripcion,
			@RequestParam("nickUsuario") String nickUsuario) throws DataAccessException, IOException {
		log.info("Editanto el premiado cuya id es : " + id);
		premiadoService.editarPremiado(id, descripcion, file, nickUsuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/borrarPremiado/{id}")
	public ResponseEntity<?> deletePremiado(@PathVariable(name = "id") Integer id) {
		premiadoService.deletePremiadoById(id);
		return ResponseEntity.ok().build();
	}

}

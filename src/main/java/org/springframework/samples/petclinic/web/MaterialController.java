package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.BodyMaterial;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.service.MaterialService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/materiales")
public class MaterialController {
	
	private MaterialService materialService;

	@Autowired
	public MaterialController(MaterialService materialService) {
		super();
		this.materialService = materialService;
	}
	
	@GetMapping("/getMaterialByProfesor/{nickProfesor}")
	public ResponseEntity<?>getMaterialByProfesor(@PathVariable("nickProfesor")String nickProfesor){
		return ResponseEntity.ok(materialService.getMaterialPorProfesor(nickProfesor));
	}
	
	@GetMapping("/getMaterialByAlumno/{nickAlumno}")
	public ResponseEntity<?>getMaterialByAlumno(@PathVariable("nickAlumno")String nickAlumno){
		return ResponseEntity.ok(materialService.getMaterialPorAlumno(nickAlumno));
	}
	
	@PostMapping(value="/añadirMaterial/{nickProfesor}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?>añadirMaterial(@PathVariable("nickProfesor") String nickProfesor, @Valid @ModelAttribute BodyMaterial body, BindingResult result) throws IOException{
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}else {
			log.info("Añadiendo PDF con nombre: " + body.getPdf().getOriginalFilename());
			Material m = materialService.uploadMaterial(body.getPdf(), nickProfesor, body.getTipoMaterial());
			return ResponseEntity.ok(m);
		}	
	}
	
}

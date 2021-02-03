package org.springframework.samples.petclinic.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.service.MaterialService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/materiales")
public class MaterialController {
	
	private MaterialService materialService;

	@Autowired
	public MaterialController(MaterialService materialService) {
		super();
		this.materialService = materialService;
	};
	
	@GetMapping("/getMaterialByProfesor/{nickProfesor}")
	public ResponseEntity<?>getMaterialByProfesor(@PathVariable("nickProfesor")String nickProfesor){
		return ResponseEntity.ok(materialService.getMaterialPorProfesor(nickProfesor));
	}
	
	@GetMapping("/getMaterialByAlumno/{nickAlumno}")
	public ResponseEntity<?>getMaterialByAlumno(@PathVariable("nickAlumno")String nickAlumno){
		return ResponseEntity.ok(materialService.getMaterialPorAlumno(nickAlumno));
	}
	
	@PostMapping("/añadirMaterial/{nickProfesor}")
	public ResponseEntity<?>añadirMaterial(@PathVariable("nickProfesor") String nickProfesor, @RequestParam(value="pdf",required=false) MultipartFile pdf) throws IOException{
		log.info("he entrado en añadirMaterial");
		if(pdf==null) {
			return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}else {
			Material m = materialService.uploadMaterial(pdf, nickProfesor);
			return ResponseEntity.ok(m);
		}	
	}
	
	
	
	
}

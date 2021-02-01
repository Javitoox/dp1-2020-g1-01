package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.MaterialService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	

}

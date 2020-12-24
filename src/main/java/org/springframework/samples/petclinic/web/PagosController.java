package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/pagos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class PagosController {
	
	private final PagoService pagoService;

	public PagosController(PagoService pagoService) {
		this.pagoService = pagoService;
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Alumno>> listadoAlumnosPagos() {
		List<Alumno> all =  pagoService.getStudentsPayment();
		return ResponseEntity.ok(all);
	}
	
	


}

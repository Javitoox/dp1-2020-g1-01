package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/noPaid")
	public ResponseEntity<List<Alumno>> listadoAlumnosNoPagos() {
		List<Alumno> all =  pagoService.getStudentsNoPayment();
		return ResponseEntity.ok(all);
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody Pago resource, BindingResult result){
		log.info("Solicitando crear pago: {}", resource);
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}else {
			pagoService.savePaid(resource);
			return new ResponseEntity<>("Pago creado correctamente", HttpStatus.CREATED);
		}
	}
	


}

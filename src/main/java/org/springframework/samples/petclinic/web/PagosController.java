package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping
	public ResponseEntity<Set<String>> allPayments(){
		Set<String> all = pagoService.getAllPayments();
		return ResponseEntity.ok(all);
	}
	
	
	@GetMapping("/{concepto}")
	public ResponseEntity<List<Alumno>> listadoAlumnosPagos(@PathVariable("concepto") String concepto) {
		List<Alumno> all =  pagoService.getStudentsByPayment(concepto);
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/notPaid/{concepto}")
	public ResponseEntity<List<Alumno>> listadoAlumnosNoPagos(@PathVariable("concepto") String concepto) {
		List<Alumno> all =  pagoService.getStudentsNoPayment(concepto);
		return ResponseEntity.ok(all);
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody Pago resource, BindingResult result){
		log.info("Solicitando crear pago: {}", resource);
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldError(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}else {
			pagoService.savePayment(resource);
			return new ResponseEntity<>("Pago creado correctamente", HttpStatus.CREATED);
		}
	}
	


}

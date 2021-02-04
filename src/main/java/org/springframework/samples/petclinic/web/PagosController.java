package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PagosController {
	
	private final PagoService pagoService;

	public PagosController(PagoService pagoService) {
		this.pagoService = pagoService;
	}
	
	@GetMapping //profesor
	public ResponseEntity<Set<String>> allPayments(){
		Set<String> all = pagoService.getAllPayments();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/{concepto}") //profesor
	public ResponseEntity<List<Alumno>> listadoAlumnosPagos(@PathVariable("concepto") String concepto) {
		List<Alumno> all =  pagoService.getStudentsByPayment(concepto);
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/notPaid/{concepto}") //profesor
	public ResponseEntity<List<Alumno>> listadoAlumnosNoPagos(@PathVariable("concepto") String concepto) {
		List<Alumno> all =  pagoService.getStudentsNoPayment(concepto);
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/notPaidByStudent/{nickUsuario}") //alumno, profesor
	public ResponseEntity<List<String>> listadoNoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario) {
		List<String> all =  pagoService.getNoPaymentByStudent(nickUsuario);
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/paidByStudent/{nickUsuario}") //alumno
	public ResponseEntity<List<Pago>> listadoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario) {
		List<Pago> all =  pagoService.getPaymentsByStudent(nickUsuario);
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/studentsNotPaid") //profesor
	public ResponseEntity<List<String>> listadoNombreAlumnoNoPago() {
		List<String> all =  pagoService.getNameStudentByNoPago();
		return ResponseEntity.ok(all);
	}
	
	@PostMapping("/new") // profesor
	public ResponseEntity<?> create(@Valid @RequestBody Pago resource, BindingResult result) {
		log.info("Solicitando crear pago: {}", resource);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Pago>> violations = validator.validate(resource);

		if (result.hasErrors() || violations.size() > 0) {
			List<FieldError> errors = new ArrayList<>();
			if (violations.size() > 0) {
				for (ConstraintViolation<Pago> v : violations) {
					FieldError e = new FieldError("pago", v.getPropertyPath().toString(), v.getMessageTemplate());
					errors.add(e);
				}
			}
			if (result.hasErrors()) {
				errors.addAll(result.getFieldErrors());
			}
			if (resource.getConcepto().equals("null") || resource.getConcepto() == "") {
				FieldError e = new FieldError("pago", "concepto", "You have to select a concept");
				errors.add(e);
			}
			if (resource.getTipo().equals("null") || resource.getTipo() == "") {
				FieldError e = new FieldError("pago", "tipo", "You have to select a type");
				errors.add(e);
			}
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			pagoService.savePayment(resource);
			return new ResponseEntity<>("Pago creado correctamente", HttpStatus.CREATED);
		}
	}

}

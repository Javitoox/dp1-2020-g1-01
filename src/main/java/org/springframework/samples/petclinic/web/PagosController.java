package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.TipoPago;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.samples.petclinic.service.TipoPagoService;
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
	private final TipoPagoService tipoPagoService;
	private final AlumnoService alumnoService;

	@Autowired
	public PagosController(PagoService pagoService, TipoPagoService tipoPagoService, AlumnoService alumnoService) {
		this.pagoService = pagoService;
		this.tipoPagoService = tipoPagoService;
		this.alumnoService = alumnoService;
	}

	@GetMapping
	public ResponseEntity<List<String>> allPayments() {
		Set<String> all = pagoService.getAllPayments();
		List<String> allList = all.stream().collect(Collectors.toList());
		return ResponseEntity.ok(allList);
	}

	@GetMapping("/{concepto}")
	public ResponseEntity<List<Alumno>> listadoAlumnosPagos(@PathVariable("concepto") String concepto) {
		List<Alumno> all = pagoService.getStudentsByPayment(concepto);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/notPaid/{concepto}")
	public ResponseEntity<List<Alumno>> listadoAlumnosNoPagos(@PathVariable("concepto") String concepto) {
		List<Alumno> all = pagoService.getStudentsNoPayment(concepto);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/notPaidByStudent/{nickUsuario}")
	public ResponseEntity<List<String>> listadoNoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario) {
		List<String> all = pagoService.getNoPaymentByStudent(nickUsuario);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/paidByStudent/{nickUsuario}")
	public ResponseEntity<List<Pago>> listadoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario) {
		List<Pago> all = pagoService.getPaymentsByStudent(nickUsuario);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/studentsNotPaid")
	public ResponseEntity<List<String>> listadoNombreAlumnoNoPago() {
		List<String> all = pagoService.getNameStudentByNoPago();
		return ResponseEntity.ok(all);
	}

	@PostMapping("/new/{tipoPago}")
	public ResponseEntity<?> create(@Valid @RequestBody Pago resource, @PathVariable("tipoPago") String tipoPago,
			BindingResult result) {
		TipoPago t = tipoPagoService.getType(tipoPago);
		resource.setTipo(t);

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
			if (resource.getAlumnos().getNickUsuario().equals("null") || resource.getAlumnos().getNickUsuario() == "") {
				FieldError e = new FieldError("pago", "nickUsuario", "You must write a nickname");
				errors.add(e);
			}
			if (resource.getConcepto().equals("null") || resource.getConcepto() == "") {
				FieldError e = new FieldError("pago", "concepto", "You must select a concept");
				errors.add(e);
			}
			if (resource.getTipo().getTipo().equals("null") || resource.getTipo().getTipo() == "") {
				FieldError e = new FieldError("pago", "tipo", "You must select a type");
				errors.add(e);
			}
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			Alumno alumno = alumnoService.getAlumno(resource.getAlumnos().getNickUsuario());
			LocalDate fb = alumno.getFechaBaja();
			if (fb == null) {
				pagoService.savePayment(resource);
				return new ResponseEntity<>("Pago creado correctamente", HttpStatus.CREATED);

			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

		}

	}

}

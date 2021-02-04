package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.TipoPago;
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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class PagosController {
	
	private final PagoService pagoService;
	private final TipoPagoService tipoPagoService;

	public PagosController(PagoService pagoService, TipoPagoService tipoPagoService) {
		this.pagoService = pagoService;
		this.tipoPagoService = tipoPagoService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<String>> allPayments(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			Set<String> all = pagoService.getAllPayments();
			List<String> allList = all.stream().collect(Collectors.toList());
			return ResponseEntity.ok(allList);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	
	@GetMapping("/{concepto}")
	public ResponseEntity<List<Alumno>> listadoAlumnosPagos(@PathVariable("concepto") String concepto, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			List<Alumno> all =  pagoService.getStudentsByPayment(concepto);
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/notPaid/{concepto}")
	public ResponseEntity<List<Alumno>> listadoAlumnosNoPagos(@PathVariable("concepto") String concepto, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
			List<Alumno> all =  pagoService.getStudentsNoPayment(concepto);
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	@GetMapping("/notPaidByStudent/{nickUsuario}") /*¿Quién puede acceder aquí?*/
	public ResponseEntity<List<String>> listadoNoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario,  HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && (session.getAttribute("type") == "alumno" || session.getAttribute("type") == "profesor" )) {
			List<String> all =  pagoService.getNoPaymentByStudent(nickUsuario);
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		
	}
	
	@GetMapping("/paidByStudent/{nickUsuario}") /*¿Quién puede acceder aquí?*/
	public ResponseEntity<List<Pago>> listadoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario,  HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "alumno" ) {
			List<Pago> all =  pagoService.getPaymentsByStudent(nickUsuario);
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		
	}
	
	@GetMapping("/studentsNotPaid") /*¿Quién puede acceder aquí?*/
	public ResponseEntity<List<String>> listadoNombreAlumnoNoPago(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			List<String> all =  pagoService.getNameStudentByNoPago();
			return ResponseEntity.ok(all);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		
	}
	
	
	@PostMapping("/new/{tipoPago}")
	public ResponseEntity<?> create(@Valid @RequestBody Pago resource, @PathVariable("tipoPago") String tipoPago ,BindingResult result, HttpServletRequest request){
		HttpSession session = request.getSession(false);	
		TipoPago t = tipoPagoService.getType(tipoPago);
		resource.setTipo(t);

		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Sesión iniciada como: " + session.getAttribute("type"));
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
			}else {
						
					pagoService.savePayment(resource);				
					return new ResponseEntity<>("Pago creado correctamente", HttpStatus.CREATED);

					
					

					
				
			
			}
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	


}

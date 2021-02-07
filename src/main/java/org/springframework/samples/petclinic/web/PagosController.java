package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
	private final AlumnoService alumnoService;

	@Autowired
	public PagosController(PagoService pagoService, AlumnoService alumnoService) {
		this.pagoService = pagoService;
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
	public ResponseEntity<List<String>> listadoNoPagosPorAlumnoByStudent(@PathVariable("nickUsuario") String nickUsuario,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getUsername().equals(nickUsuario)) {
			List<String> all = pagoService.getNoPaymentByStudent(nickUsuario);
			return ResponseEntity.ok(all);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	}
	
	@GetMapping("/notPaidProfesor/{nickUsuario}") /*HAY QUE LINKEAR ESTE MÉTODO CON EL FRONTEND PARA LA VISTA DEL PROFESOR*/
	public ResponseEntity<List<String>> listadoNoPagosPorAlumnoByProfesor(@PathVariable("nickUsuario") String nickUsuario) {
			List<String> all = pagoService.getNoPaymentByStudent(nickUsuario);
			return ResponseEntity.ok(all);
	}

	@GetMapping("/paidByStudent/{nickUsuario}")
	public ResponseEntity<List<Pago>> listadoPagosPorAlumno(@PathVariable("nickUsuario") String nickUsuario,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getUsername().equals(nickUsuario)) {
			List<Pago> all = pagoService.getPaymentsByStudent(nickUsuario);
			return ResponseEntity.ok(all);
		} else {
			log.warn("Acceso prohibido");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/studentsNotPaid")
	public ResponseEntity<List<String>> listadoNombreAlumnoNoPago() {
		List<String> all = pagoService.getNameStudentByNoPago();
		return ResponseEntity.ok(all);
	}

	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody Pago resource, BindingResult result) {
		log.info("Solicitando crear pago: {}", resource);
		if (result.hasErrors() || resource.getAlumnos().getNickUsuario() == null
				|| resource.getAlumnos().getNickUsuario() == "" || resource.getConcepto() == null
				|| resource.getConcepto() == "" || resource.getTipo().getTipo() == null
				|| resource.getTipo().getTipo().equals("")) {
			List<FieldError> errors = new ArrayList<>();
			if (result.hasErrors()) {
				errors.addAll(result.getFieldErrors());
			}
			if (resource.getAlumnos().getNickUsuario() == null || resource.getAlumnos().getNickUsuario() == "") {
				FieldError e = new FieldError("pago", "nickUsuario", "You must write a nickname");
				errors.add(e);
			}
			if (resource.getConcepto() == null || resource.getConcepto() == "") {
				FieldError e = new FieldError("pago", "concepto", "You must select a concept");
				errors.add(e);
			}
			if (resource.getTipo().getTipo() == null || resource.getTipo().getTipo().equals("")) {
				FieldError e = new FieldError("pago", "tipo", "You must select a type");
				errors.add(e);
			}
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			Alumno alumno = alumnoService.getAlumno(resource.getAlumnos().getNickUsuario());
			if (alumno == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
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

}

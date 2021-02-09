package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.util.SolicitudValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/requests")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
public class SolicitudController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final SolicitudService solicitudServ;

	@Autowired
	public SolicitudController(SolicitudService solicitudServ, AlumnoService alumServ) {
		this.solicitudServ = solicitudServ;
	}

	@InitBinder("solicitud")
	public void initEventoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new SolicitudValidator());
	}

	@GetMapping("/pending")
	public ResponseEntity<?> getSolicitudes() {
		return ResponseEntity.ok(solicitudServ.getAllSolicitudes());
	}

	@PutMapping("/decline/{nickUsuario}")
	public ResponseEntity<?> declineRequest(@PathVariable("nickUsuario") String nickUsuario) {
		Alumno alumnoDenegado = solicitudServ.getAlumno(nickUsuario);
		solicitudServ.declineRequest(alumnoDenegado);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/accept/{nickUsuario}")
	public ResponseEntity<?> acceptRequest(@PathVariable("nickUsuario") String nickUsuario) {
		Alumno alumnoAceptado = solicitudServ.getAlumno(nickUsuario);
		log.info("ALUMNO ACEPTADO: " + alumnoAceptado);
		solicitudServ.acceptRequest(alumnoAceptado);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/sending")
	public ResponseEntity<?> sending(@Valid @RequestBody Solicitud solicitud, BindingResult result) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Solicitud>> violations = validator.validate(solicitud);
		if (result.hasErrors() || violations.size() > 0) {
			List<FieldError> errors = new ArrayList<>();
			if (violations.size() > 0) {
				for (ConstraintViolation<Solicitud> v : violations) {
					FieldError e = new FieldError("solicitud", v.getPropertyPath().toString(), v.getMessageTemplate());
					errors.add(e);
				}
			}
			if (result.hasErrors()) {
				errors.addAll(result.getFieldErrors());
			}
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			Alumno alumno = null;
			try {
				alumno = solicitudServ.getAlumnoByIdOrNif(solicitud.getAlumno().getNickUsuario(),
						solicitud.getAlumno().getDniUsuario());
			} catch (Exception e) {
				log.info("Duplicated users");
				return new ResponseEntity<>("The student already exists and his credentials are incorrect",
						HttpStatus.OK);
			}
			if (alumno == null) {
				solicitud.getAlumno().setContraseya(passwordEncoder.encode(solicitud.getAlumno().getContraseya()));
				solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
				solicitud.getAlumno().setVersion(0);
				solicitudServ.saveRequest(solicitud);
				log.info("New student´s request with username: " + solicitud.getAlumno().getNickUsuario());
				return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			}else if(alumno.getFechaMatriculacion() != null) {
				return new ResponseEntity<>("The student already belongs to the academy",
						HttpStatus.OK);
			}else if(alumno.getFechaBaja() != null) {
				return new ResponseEntity<>("The student has already been withdrawn",
						HttpStatus.OK);
			}
			else if (passwordEncoder.matches(solicitud.getAlumno().getContraseya(), alumno.getContraseya())
					&& alumno.getNickUsuario().equals(solicitud.getAlumno().getNickUsuario())) {
				solicitud.getAlumno().setContraseya(passwordEncoder.encode(solicitud.getAlumno().getContraseya()));
				solicitud.getAlumno().setVersion(alumno.getVersion());
				solicitudServ.saveRequest(solicitud);
				log.info("Update student´s request with username: " + solicitud.getAlumno().getNickUsuario());
				return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			} else {
				log.warn("Access denied: " + solicitud.getAlumno().getNickUsuario());
				return new ResponseEntity<>("The student already exists and his credentials are incorrect",
						HttpStatus.OK);
			}
		}
	}

	@PostMapping("/sendingAll")
	public ResponseEntity<?> sendingAll(@Valid @RequestBody Solicitud solicitud, BindingResult result) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Solicitud>> violations = validator.validate(solicitud);
		if (result.hasErrors() || violations.size() > 0) {
			List<FieldError> errors = new ArrayList<>();
			if (violations.size() > 0) {
				for (ConstraintViolation<Solicitud> v : violations) {
					FieldError e = new FieldError("solicitud", v.getPropertyPath().toString(), v.getMessageTemplate());
					errors.add(e);
				}
			}
			if (result.hasErrors()) {
				errors.addAll(result.getFieldErrors());
			}
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			Alumno alumno = null;
			Tutor tutor = null;
			try {
				alumno = solicitudServ.getAlumnoByIdOrNif(solicitud.getAlumno().getNickUsuario(),
						solicitud.getAlumno().getDniUsuario());
				tutor = solicitudServ.getTutorByIdOrNif(solicitud.getTutor().getNickUsuario(),
						solicitud.getTutor().getDniUsuario());
			} catch (Exception e) {
				log.info("Duplicated users");
				return new ResponseEntity<>("Student or tutor already exist and their credentials are incorrect",
						HttpStatus.OK);
			}
			if (alumno == null && tutor == null) {
				solicitud.getAlumno().setContraseya(passwordEncoder.encode(solicitud.getAlumno().getContraseya()));
				solicitud.getTutor().setContraseya(passwordEncoder.encode(solicitud.getTutor().getContraseya()));
				solicitud.getAlumno().setTutores(solicitud.getTutor());
				solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
				solicitud.getTutor().setFechaSolicitud(LocalDate.now());
				solicitud.getAlumno().setVersion(0);
				solicitudServ.saveRequest(solicitud);
				log.info("New student´s request with username: " + solicitud.getAlumno().getNickUsuario());
				log.info("New tutor´s request with username: " + solicitud.getTutor().getNickUsuario());
				return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			} else if(alumno != null && alumno.getFechaMatriculacion() != null) {
				return new ResponseEntity<>("The student already belongs to the academy",
						HttpStatus.OK);
			}else if(alumno != null && alumno.getFechaBaja() != null) {
				return new ResponseEntity<>("The student has already been withdrawn",
						HttpStatus.OK);
			}else if (alumno != null
					&& passwordEncoder.matches(solicitud.getAlumno().getContraseya(), alumno.getContraseya())
					&& alumno.getNickUsuario().equals(solicitud.getAlumno().getNickUsuario()) && tutor == null) {
				solicitud.getAlumno().setContraseya(passwordEncoder.encode(solicitud.getAlumno().getContraseya()));
				solicitud.getAlumno().setTutores(solicitud.getTutor());
				solicitud.getTutor().setFechaSolicitud(LocalDate.now());
				solicitud.getAlumno().setVersion(alumno.getVersion());
				solicitudServ.saveRequest(solicitud);
				log.info("Update student´s request with username: " + solicitud.getAlumno().getNickUsuario());
				log.info("New tutor´s request with username: " + solicitud.getTutor().getNickUsuario());
				return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			} else if (alumno == null && tutor != null
					&& passwordEncoder.matches(solicitud.getTutor().getContraseya(), tutor.getContraseya())
					&& tutor.getNickUsuario().equals(solicitud.getTutor().getNickUsuario())) {
				solicitud.getTutor().setContraseya(passwordEncoder.encode(solicitud.getTutor().getContraseya()));
				solicitud.getAlumno().setTutores(solicitud.getTutor());
				solicitud.getAlumno().setFechaSolicitud(LocalDate.now());
				solicitud.getAlumno().setVersion(0);
				solicitudServ.saveRequest(solicitud);
				log.info("New student´s request with username: " + solicitud.getAlumno().getNickUsuario());
				log.info("Update tutor´s request with username: " + solicitud.getTutor().getNickUsuario());
				return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			} else if (alumno != null
					&& passwordEncoder.matches(solicitud.getAlumno().getContraseya(), alumno.getContraseya())
					&& alumno.getNickUsuario().equals(solicitud.getAlumno().getNickUsuario()) && tutor != null
					&& passwordEncoder.matches(solicitud.getTutor().getContraseya(), tutor.getContraseya())
					&& tutor.getNickUsuario().equals(solicitud.getTutor().getNickUsuario())) {
				solicitud.getAlumno().setContraseya(passwordEncoder.encode(solicitud.getAlumno().getContraseya()));
				solicitud.getTutor().setContraseya(passwordEncoder.encode(solicitud.getTutor().getContraseya()));
				solicitud.getAlumno().setTutores(solicitud.getTutor());
				solicitud.getAlumno().setVersion(alumno.getVersion());
				solicitudServ.saveRequest(solicitud);
				log.info("Update student´s request with username: " + solicitud.getAlumno().getNickUsuario());
				log.info("Update tutor´s request with username: " + solicitud.getTutor().getNickUsuario());
				return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
			} else {
				log.warn("Access denied: " + solicitud.getAlumno().getNickUsuario() + " and "
						+ solicitud.getTutor().getNickUsuario());
				return new ResponseEntity<>("Student or tutor already exist and their credentials are incorrect",
						HttpStatus.OK);
			}
		}
	}

}

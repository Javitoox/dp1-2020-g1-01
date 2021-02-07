package org.springframework.samples.petclinic.web;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.util.AlumnoValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/alumnos")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AlumnoController {

	private AlumnoService alumnoServ;
	private GrupoService grupoService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public AlumnoController(AlumnoService alumnoServ, GrupoService grupoService, PasswordEncoder passwordEncoder) {
		this.alumnoServ = alumnoServ;
		this.grupoService = grupoService;
		this.passwordEncoder = passwordEncoder;
	}

	@InitBinder("alumno")
	public void initEventoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new AlumnoValidator());
	}

	@PutMapping("/editStudent") 
	public ResponseEntity<?> processUpdateAlumnoForm(@Valid @RequestBody Alumno alumno, BindingResult result,
			Authentication authentication) {
		log.info(alumno.getContraseya());
		try {
			alumnoServ.getAlumnoByIdOrNif(alumno.getNickUsuario(), alumno.getDniUsuario());
		} catch (Exception e) {
			log.info("Duplicated users");
			return new ResponseEntity<>("The student already exists and his credentials are incorrect",
					HttpStatus.OK);
		}
		boolean comprobation=true;
		if(alumno.getContraseya()==null || alumno.getContraseya()=="") {
			Alumno a = alumnoServ.getAlumnoByIdOrNif(alumno.getNickUsuario(), "");
			alumno.setContraseya(a.getContraseya());
			comprobation=false;
		}
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Alumno>> violations = validator.validate(alumno);

		if (result.hasErrors() || violations.size() > 0) {
			List<FieldError> errors = new ArrayList<>();
			if (violations.size() > 0) {
				for (ConstraintViolation<Alumno> v : violations) {
					FieldError e = new FieldError("contraseya", v.getPropertyPath().toString(), v.getMessageTemplate());
					errors.add(e);
				}
			}
			if (result.hasErrors()) {
				errors.addAll(result.getFieldErrors());
			}
	
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			log.info("Entra aqui con contraseña : " + alumno.getContraseya());
			if(comprobation==true){
			alumno.setContraseya(passwordEncoder.encode(alumno.getContraseya()));
			}
			alumnoServ.saveAlumno(alumno);
			return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
		}
	}

	@PutMapping("/editPersonalInfo")
	public ResponseEntity<?> processUpdateStudentPersonal(@RequestBody Alumno student, BindingResult result) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Alumno>> violations = validator.validate(student);
		if (violations.size() > 0) {
			List<FieldError> errors = new ArrayList<>();
			if (violations.size() > 0) {
				for (ConstraintViolation<Alumno> v : violations) {
					FieldError e = new FieldError("solicitud", v.getPropertyPath().toString(), v.getMessageTemplate());
					errors.add(e);
				}
			}
			return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
		this.alumnoServ.saveAlumno(student);
		return new ResponseEntity<>("Successful shipment", HttpStatus.CREATED);
		}
		}

	//}

	@GetMapping("/getStudentInfo/{nickUsuario}")
	public ResponseEntity<Alumno> getStudentInfo(@PathVariable("nickUsuario") String nick,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getUsername().equals(nick)) {
			Alumno alumno = alumnoServ.getAlumno(nick);
			return ResponseEntity.ok(alumno);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> listAlumnos() {
		List<Alumno> allStudents = alumnoServ.getAllAlumnos();
		return ResponseEntity.ok(allStudents);
	}

	@GetMapping("/ableToDelete")
	public ResponseEntity<?> listAlumnosToDelete() {
		List<String> allStudents = alumnoServ.getStudentsToDelete();
		return ResponseEntity.ok(allStudents);
	}

	@GetMapping("/studentsWithNoGroups")
	public ResponseEntity<?> listAlumnosWithNoGroups() {
		List<String> allStudents = alumnoServ.getStudentsWithNoGroups();
		return ResponseEntity.ok(allStudents);
	}

	@GetMapping("/studentsWithNoTutors")
	public ResponseEntity<?> listAlumnosWithNoTutors() {
		List<String> allStudents = alumnoServ.getStudentsWithNoTutors();
		return ResponseEntity.ok(allStudents);
	}

	@GetMapping("/getByCourse/{course}")
	public ResponseEntity<?> listStudentsByCourse(@PathVariable("course") TipoCurso cursoDeIngles) {
		log.info("Obteniendo alumnos del curso: " + cursoDeIngles);
		List<Alumno> allStudentsByCourse = alumnoServ.getStudentsByCourse(cursoDeIngles);
		return ResponseEntity.ok(allStudentsByCourse);
	}

	@GetMapping("/{nombreGrupo}")
	public ResponseEntity<List<Alumno>> getPersonasByNameOfGroup(@PathVariable("nombreGrupo") String nombreGrupo) {
		log.info("Obteniendo alumnos del curso: " + nombreGrupo);
		List<Alumno> studentsByGroup = alumnoServ.getStudentsPerGroup(nombreGrupo);
		return ResponseEntity.ok(studentsByGroup);
	}

	@GetMapping("/{nickTutor}/allMyStudents")
	public ResponseEntity<?> getStudentsByTutor(@PathVariable("nickTutor") String nickTutor,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getUsername().equals(nickTutor)) {
			log.info("Obteniendo alumnos del tutor: " + nickTutor);
			List<Alumno> studentsByTutor = alumnoServ.getAllMyStudents(nickTutor);
			return ResponseEntity.ok(studentsByTutor);
		} else {
			log.warn("El nick pasado por parámetros no coincide con el nick logeado");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/assignStudent")
	public ResponseEntity<?> assignStudent(@Valid @RequestBody Alumno alumno, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} else {
			if (alumno.getGrupos().getNombreGrupo() != null) {
				Integer numAlumnosGrupo = grupoService.numAlumnos(alumno.getGrupos().getNombreGrupo());
				if (numAlumnosGrupo < 12) {
					this.alumnoServ.saveAlumnAsign(alumno);
					return new ResponseEntity<>("Successful edit", HttpStatus.CREATED);
				} else {
					return new ResponseEntity<>("El grupo tiene más de 12 alumnos", HttpStatus.ALREADY_REPORTED);
				}
			}
			this.alumnoServ.saveAlumnAsign(alumno);
			return new ResponseEntity<>("Successful edit", HttpStatus.CREATED);
		}
	}

	@DeleteMapping("/delete/{nickUsuario}")
	public ResponseEntity<?> deleteStudent(@PathVariable("nickUsuario") String nickUsuario) {
		log.info("Solicitando borrar alumno: {}", nickUsuario);
		if (alumnoServ.getStudentsToDelete().contains(nickUsuario.toString())) {
			Alumno a = alumnoServ.getAlumno(nickUsuario);
			alumnoServ.deleteStudents(a);
			return new ResponseEntity<>("Alumno dado de baja correctamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se puede borrar el alumno porque tiene pagos pendientes",
					HttpStatus.BAD_REQUEST);
		}
	}

}

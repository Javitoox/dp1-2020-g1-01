package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SolicitudValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Solicitud.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Solicitud solicitud = (Solicitud) target;
		Tutor tutor = solicitud.getTutor();
		Alumno alumno = solicitud.getAlumno();
		LocalDate fechaAlumno = alumno.getFechaNacimiento();
		if(fechaAlumno!=null) {
			Integer edadAlumno = calcularEdad(fechaAlumno);
			log.info("Edad alumno: "+edadAlumno);
			
			if(edadAlumno<18 && tutor==null) {
				errors.rejectValue("alumno.fechaNacimiento", "If you are a minor, you must have an assigned tutor",
						"If you are a minor, you must have an assigned tutor");
			}
			
			
		}
		
		String regPass = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";
		
		if(alumno.getContraseya()!=null && !Pattern.matches(regPass, alumno.getContraseya())) {
			errors.rejectValue("alumno.contraseya", 
					"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase",
					"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase");
		}
		
		if(tutor!=null && tutor.getContraseya()!=null && !Pattern.matches(regPass, tutor.getContraseya())) {
			errors.rejectValue("tutor.contraseya", 
					"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase",
					"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase");
		}
		
		
	}
	
	private Integer calcularEdad(LocalDate fecha) {
		LocalDate fechaActual = LocalDate.now();
		Period periodo = Period.between(fecha, fechaActual);
		return periodo.getYears();
	}

}

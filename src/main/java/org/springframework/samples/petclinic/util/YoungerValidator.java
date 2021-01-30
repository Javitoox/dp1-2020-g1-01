package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YoungerValidator implements Validator{

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
		LocalDate fechaAlumno = solicitud.getAlumno().getFechaNacimiento();
		Integer edadAlumno = calcularEdad(fechaAlumno);
		log.info("Edad alumno: "+edadAlumno);
		
		if(edadAlumno<18 && tutor==null) {
			errors.rejectValue("alumno.fechaNacimiento", "If you are a minor, you must have an assigned tutor",
					"If you are a minor, you must have an assigned tutor");
		}
	}
	
	private Integer calcularEdad(LocalDate fecha) {
		LocalDate fechaActual = LocalDate.now();
		Period periodo = Period.between(fecha, fechaActual);
		return periodo.getYears();
	}

}

package org.springframework.samples.petclinic.util;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Evento;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DateEventValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Evento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Evento evento = (Evento) target;
		LocalDate start = evento.getStart();
		LocalDate end = evento.getEnd();
		
		if(end != null && start.isAfter(end)) {
			errors.rejectValue("start", "The start date cannot be later than the end date",
					"The start date cannot be later than the end date");
		}
	}

}

package org.springframework.samples.petclinic.util;

import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlumnoValidator implements Validator {@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
	return Alumno.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Alumno alumno = (Alumno) target;
		String regPass = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";
		
		if(alumno.getContraseya()!="" && !Pattern.matches(regPass, alumno.getContraseya())) {
			errors.rejectValue("contraseya", 
					"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase",
					"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase");
		}
		
	}

}

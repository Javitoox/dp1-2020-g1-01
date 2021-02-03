package org.springframework.samples.petclinic.model;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solicitud {
	
	@Valid
	private Alumno alumno;
	
	@Valid
	private Tutor tutor;

}

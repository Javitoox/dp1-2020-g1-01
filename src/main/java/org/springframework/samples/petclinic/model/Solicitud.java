package org.springframework.samples.petclinic.model;

import javax.validation.Valid;

import lombok.Data;

@Data
public class Solicitud {
	
	@Valid
	private Alumno alumno;
	
	@Valid
	private Tutor tutor;

}

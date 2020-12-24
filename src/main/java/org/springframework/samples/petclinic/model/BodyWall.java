package org.springframework.samples.petclinic.model;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BodyWall {
	
	@Valid
	private String nickusuario;
	
	@Valid
	private MultipartFile photo;
	
	@Valid
	private String description;
	
}

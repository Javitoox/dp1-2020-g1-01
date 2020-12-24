package org.springframework.samples.petclinic.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BodyWall {
	
	private String nickusuario;
	
	private MultipartFile photo;
	
	private String description;
	
}

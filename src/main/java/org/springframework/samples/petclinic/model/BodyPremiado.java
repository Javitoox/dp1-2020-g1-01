package org.springframework.samples.petclinic.model;

import javax.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BodyPremiado {

	@NotBlank(message = "Required field")
	private String nickUsuario;
	
	@NotBlank(message = "Required field")
	private String description;
	
	@NotBlank(message = "Required field")
	private MultipartFile photo;

}
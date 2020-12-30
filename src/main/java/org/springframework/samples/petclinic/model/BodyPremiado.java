package org.springframework.samples.petclinic.model;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class BodyPremiado {

	@NotBlank(message = "Required field")
	private String nickUsuario;
	
	@NotBlank(message = "Required field")
	private String description;
	
	@NotNull
	private MultipartFile photo;

}
package org.springframework.samples.petclinic.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class BodyPremiado {

	@NotBlank(message = "Required field")
	private String nickUsuario;
	
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 300, message = "The maximum size is 300 characters")
	@NotBlank(message = "Required field")
	private String description;
	
	@NotNull
	private MultipartFile photo;

}
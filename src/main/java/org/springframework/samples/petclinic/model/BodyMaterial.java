package org.springframework.samples.petclinic.model;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyMaterial {
	
	@NotBlank(message="Required field")
	private String tipoMaterial;
	
	@NotNull
	private MultipartFile pdf;
}

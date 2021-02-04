package org.springframework.samples.petclinic.model;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyMaterial {
	
	@NotNull
	private MultipartFile pdf;
	
	@Valid
	private TipoMaterial tipoMaterial;
}

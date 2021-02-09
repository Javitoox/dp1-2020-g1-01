package org.springframework.samples.tea.model;

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

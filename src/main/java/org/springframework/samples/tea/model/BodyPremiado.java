package org.springframework.samples.tea.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyPremiado {

	@NotBlank(message = "Required field")
	private String nickUsuario;

	@NotBlank(message = "Required field")
	@Size(max = 300, message = "The maximum size is 300 characters")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	private String description;

	@NotNull
	private MultipartFile photo;

}

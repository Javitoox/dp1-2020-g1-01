package org.springframework.samples.petclinic.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EventoId implements Serializable{
	
	@Column(name="nombre")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 40, message = "The maximum size is 40 characters")
	@NotBlank(message = "Required field")
	private String nombre;
	
	@Column(name="fecha")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "The date must be a future date")
	@NotNull(message = "Required field")
	private LocalDate fecha;
	
}

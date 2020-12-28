package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name="eventos")
@Data
public class Evento extends BaseEntity{
	
	@Column(name="title")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 40, message = "The maximum size is 40 characters")
	@NotBlank(message = "Required field")
	private String title;
	
	@Column(name="start")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "The date must be a future date")
	@NotNull(message = "Required field")
	private LocalDate start;
	
	@Column(name="end")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "The date must be a future date")
	private LocalDate end;
	
	@Column(name="descripcion")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 300, message = "The maximum size is 300 characters")
	@NotBlank(message = "Required field")
	private String descripcion;
	
	@ManyToOne(optional=false)
    private TipoEvento tipo;
	
	@OneToMany(cascade=CascadeType.ALL)
    private Collection<Inscripcion> inscripciones;

}

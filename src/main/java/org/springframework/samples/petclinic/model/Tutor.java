package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="tutores")
public class Tutor extends Usuario{
	
	@Column(name="fecha_matriculacion")
	private LocalDate fechaMatriculacion;
	
	@NotNull
	@Column(name="fecha_solicitud")
	private LocalDate fechaSolicitud;
}


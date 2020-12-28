package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="inscripciones")
@Data
public class Inscripcion extends BaseEntity{
	
	@Column(name="fecha")
	private LocalDate fecha;
	
	@Column(name="registrado")
	@NotNull
	private Boolean registrado;
	
	@ManyToOne(optional=true)
	private TipoPago tipo;

}

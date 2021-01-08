package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="pagos")
public class Pago extends BaseEntity{
	
	@Column(name="tipo")
	@NotEmpty(message = "Required field")
	private String tipo;
	
	@Column(name="concepto")
	@NotEmpty(message = "Required field")
	private String concepto;
	
	@Column(name="fecha")
	private LocalDate fecha;
		   
    @ManyToOne(optional=false)
    private Alumno alumnos;
	

}

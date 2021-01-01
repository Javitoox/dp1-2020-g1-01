package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="pagos")
public class Pago extends BaseEntity{
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="concepto")
	private String concepto;
	
	@Column(name="fecha")
	private LocalDate fecha;
		   
    @ManyToOne(optional=false)
    private Alumno alumnos;
	

}

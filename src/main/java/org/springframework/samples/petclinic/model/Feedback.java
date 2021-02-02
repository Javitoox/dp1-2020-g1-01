package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="feedbacks")
@Getter
@Setter
public class Feedback extends BaseEntity {

	@Column(name="completado")
	private Boolean completado;
	
	@Column(name="dia_entrega")
	private LocalDate diaEntrega;
	
	@Column(name="valoracion")
	private Integer valoracion;
	
	@Column(name="comentario")
	private String comentario;
	
	@ManyToOne(optional=false)
	private Material materiales;
	
	@ManyToOne(optional=false)
	private Alumno alumnos;
	
}

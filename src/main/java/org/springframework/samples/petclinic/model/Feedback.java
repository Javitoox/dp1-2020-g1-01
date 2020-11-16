package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="feedback")
public class Feedback {

	@Id
	@Column(name="idfeedback")
	private Integer idfeedback;
	
	@Enumerated(EnumType.STRING)
	@Column(name="numestrellas")
	private NumEstrellas numestrellas;
	
	@Column(name="comentario")
	private String comentario;
	
	@ManyToOne
    @JoinColumn(name = "nickusuario")
    private Alumno alumno;
	
	@ManyToOne
    @JoinColumn(name = "id")
    private Material material;
}

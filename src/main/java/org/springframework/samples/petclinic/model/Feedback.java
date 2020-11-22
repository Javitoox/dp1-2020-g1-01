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
@Table(name="feedbacks")
public class Feedback {

	@Id
	@Column(name="id_feedback")
	private Integer idFeedback;
	
	@Enumerated(EnumType.STRING)
	@Column(name="num_estrellas")
	private NumEstrellas numEstrellas;
	
	@Column(name="comentario")
	private String comentario;
	
}

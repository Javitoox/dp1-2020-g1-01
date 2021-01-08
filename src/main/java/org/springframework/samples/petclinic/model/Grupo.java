package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name="grupos")
public class Grupo {
	@Id
	@Column(name="nombre_grupo")
	@NotEmpty(message = "Required field")
	private String nombreGrupo;
	
	@ManyToOne
	@Valid
    private Curso cursos;
	

}

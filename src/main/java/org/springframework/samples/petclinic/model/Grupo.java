package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="grupos")
public class Grupo {
	@Id
	@Column(name="nombre_grupo")
	private String nombreGrupo;
	
	@ManyToOne(optional=true)
    private Curso cursos;	
	
}

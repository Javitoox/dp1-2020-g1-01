package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cursos")
public class Curso {
	
	@Id
	@Column(name="curso_de_ingles")
	private String cursoDeIngles;
	
}
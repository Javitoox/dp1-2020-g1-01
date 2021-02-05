package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;

@Audited
@Entity
@Getter
@Setter
@Table(name="cursos")
public class Curso {
	
	@Id
	@Column(name="curso_de_ingles")
	@Enumerated(value = EnumType.STRING)
	private TipoCurso cursoDeIngles;
	
}
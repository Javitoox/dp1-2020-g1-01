package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Getter;

@Audited
@Entity
@Getter
@Table(name="cursos")
public class Curso {

	@Id
	@Column(name="curso_de_ingles")
    private String cursoDeIngles;

}

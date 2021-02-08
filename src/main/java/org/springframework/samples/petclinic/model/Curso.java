package org.springframework.samples.petclinic.model;

import javax.persistence.*;

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
    private String cursoDeIngles;

}

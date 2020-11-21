package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="cursos")
public class Curso {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name="curso_de_ingles")
	private CursoDeIngles cursoDeIngles;
	
	@OneToMany(mappedBy = "curso") //CascadeType.ALL, 
	private List<Grupo> grupos;
	

}

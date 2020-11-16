package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="curso")
public class Curso {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name="cursodeingles")
	private CursoDeIngles cursodeingles;
	
	@OneToMany(mappedBy = "curso")
	private List<Grupo>grupos;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cursomaterial", joinColumns = @JoinColumn(name = "cursodeingles"),
			inverseJoinColumns = @JoinColumn(name = "id"))
	private Set<Curso> cursos;

}

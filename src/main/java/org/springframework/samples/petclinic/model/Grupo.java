package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="grupos")
public class Grupo {
	@Id
	@Column(name="nombre_grupo")
	private String nombreGrupo;
	
	@ManyToOne
    @JoinColumn(name="curso_de_ingles")
    private Curso curso;
	
	@OneToMany(mappedBy = "grupos")
    private List<Alumno> alumnos;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "evento_grupo", joinColumns = @JoinColumn(name = "nombre_grupo"),
			inverseJoinColumns = @JoinColumn(name = "id_evento"))
	private Set<Evento> eventos;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "profesor_grupo", joinColumns = @JoinColumn(name = "nombre_grupo"),
			inverseJoinColumns = @JoinColumn(name = "nick_usuario"))
	private Set<Profesor> profesores;
}

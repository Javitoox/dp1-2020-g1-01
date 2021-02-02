package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "grupos")
	@JsonIgnore
	private Collection<Alumno> alumnos;
	
//	@OneToMany(cascade=CascadeType.ALL, mappedBy = "grupos")
//	@JsonIgnore
//	private Collection<Alumno> asignaciones;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "grupo")
	@JsonIgnore
    private Collection<AsignacionProfesor> asignaciones;
	

}

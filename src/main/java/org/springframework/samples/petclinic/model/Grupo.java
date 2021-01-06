package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
	@NotBlank(message = "Required field")
	private String nombreGrupo;
	
	@ManyToOne
    private Curso cursos;
	
//	@OneToMany(mappedBy = "grupo")
//	@JsonIgnore
//    private Set<AsignacionProfesor> asignaciones = new HashSet<>();

}

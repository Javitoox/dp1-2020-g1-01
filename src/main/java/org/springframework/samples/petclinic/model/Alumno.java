package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="alumnos")

public class Alumno extends Usuario{
	
	@Column(name="num_tareas_entregadas")
	private Integer numTareasEntregadas;
	
	@Column(name="fecha_matriculacion")
	@NotBlank
	@NotEmpty
	@NotNull
	private String fechaMatriculacion;
    
    @ManyToOne(optional=false)
    private Grupo grupos;
}
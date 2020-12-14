package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="alumnos")
@Data
public class Alumno extends Usuario{
	
	@Column(name="num_tareas_entregadas")
	private Integer numTareasEntregadas;
	
	@Column(name="fecha_matriculacion")
	private LocalDate fechaMatriculacion;
	
	@Column(name="fecha_solicitud")
	private LocalDate fechaSolicitud;
	
	@Column(name="fecha_baja")
	private LocalDate fechaBaja;
	
    @ManyToOne(optional=true)
    private Tutor tutores;
    
    @ManyToOne(optional=true)
    private Grupo grupos;
}
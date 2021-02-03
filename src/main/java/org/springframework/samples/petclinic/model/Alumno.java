package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="alumnos")
@Getter
@Setter
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
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="alumno")
    @JsonIgnore
    private Collection<Inscripcion> inscripciones;
 
}
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Audited
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

	@NotAudited
    @OneToMany(cascade=CascadeType.ALL, mappedBy="alumno")
    @JsonIgnore
    private Collection<Inscripcion> inscripciones;

 
	public String toJson() {
		LocalDate copiaFechaNacimiento = getFechaNacimiento();
		Gson json = new Gson();
		this.setFechaNacimiento(null);
		String jsonString = json.toJson(this);
		String result = jsonString.substring(0, jsonString.length() - 1) + ",\"fechaNacimiento\":\""
				+ copiaFechaNacimiento.toString() + "\"}";
		this.setFechaNacimiento(copiaFechaNacimiento);
		return result;
	}

}
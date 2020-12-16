package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="solicitudes")
@Data
public class Solicitud extends BaseEntity{
	
	@OneToOne(optional=false)
	private Alumno alumno;
	
	@OneToOne(optional=true)
	private Tutor tutor;

}

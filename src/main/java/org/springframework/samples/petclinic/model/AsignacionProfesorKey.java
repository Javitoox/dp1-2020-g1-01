package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AsignacionProfesorKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="nick_profesor")
	String nickProfesor;
	
	@Column(name="nombre_grupo") 
	String nombreGrupo;
}

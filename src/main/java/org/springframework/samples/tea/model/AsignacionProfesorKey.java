package org.springframework.samples.tea.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AsignacionProfesorKey implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="nick_profesor")
	@NotEmpty(message="required field")
	String nickProfesor;

	@Column(name="nombre_grupo")
	@NotEmpty(message = "required field")
	String nombreGrupo;
}

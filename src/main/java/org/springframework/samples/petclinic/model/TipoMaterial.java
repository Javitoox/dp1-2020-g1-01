package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Entity
@Table(name="tipos_materiales")
@Getter
public class TipoMaterial {
	
	@NotBlank(message="Required field")
	@Id
	@Column(name="tipo")
	private String tipo;
}

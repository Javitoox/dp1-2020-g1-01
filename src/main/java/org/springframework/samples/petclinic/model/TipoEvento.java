package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tipos_eventos")
@Getter
@Setter
public class TipoEvento {
	
	@Id
	@Column(name="tipo")
	private String tipo;

}

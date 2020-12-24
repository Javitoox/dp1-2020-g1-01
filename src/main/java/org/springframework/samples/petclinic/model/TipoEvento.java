package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="tipos_eventos")
@Data
public class TipoEvento {
	
	@Id
	@Column(name="tipo")
	private String tipo;

}

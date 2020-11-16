package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="solicitud")
public class Solicitud extends BaseEntity{

	@Column(name="fechasolicitud")
	private String fechasolicitud;
	
	@ManyToOne
    @JoinColumn(name = "nickusuario")
    private Usuario usuario;
}

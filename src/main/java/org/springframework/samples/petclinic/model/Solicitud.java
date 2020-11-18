package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="solicitudes")
public class Solicitud extends BaseEntity{

	@Column(name="fecha_solicitud")
	private String fechaSolicitud;
	
	@ManyToOne
    @JoinColumn(name = "nick_usuario")
    private Usuario usuarios;
}

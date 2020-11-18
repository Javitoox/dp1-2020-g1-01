package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="pagos")
public class Pago extends BaseEntity{
	
	@Column(name="concepto")
	private String concepto;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_pago")
	private TipoPago tipo;
	
	@Column(name="fecha")
	private String fechaEmision;
	
	@ManyToOne
	@JoinColumn(name="nick_usuario")
	private Alumno alumno;
	

}

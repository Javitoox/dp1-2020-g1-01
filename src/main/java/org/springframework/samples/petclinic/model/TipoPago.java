package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Entity
@Table(name="tipos_pagos")
@Getter
public class TipoPago {

	@Id
	@Column(name="tipo")
	@NotBlank(message="No puede ser nulo")
	private String tipo;
	
}

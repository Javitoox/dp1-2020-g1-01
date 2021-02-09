package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="tipos_pagos")
@Getter
public class TipoPago {

	@Id
	@Column(name="tipo")
	private String tipo;
	
}

package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="premiados")
public class Premiado{
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Id
	@Column(name="fecha_premiado")
	private String fechaPremiado;
	
	@ManyToOne
    @JoinColumn(name = "nick_usuario")
    private Alumno alumno;
	
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "id") private WallofFame wallOfFame;
	 */
}

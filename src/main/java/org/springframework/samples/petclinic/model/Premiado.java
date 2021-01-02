package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="premiados")
@Getter
@Setter
public class Premiado extends BaseEntity {

    @Column(name="descripcion")
    private String descripcion;
    
    @Column(name="foto")
    private String foto;
    
    @ManyToOne(optional=false)
    private Alumno alumnos;

    @ManyToOne(optional=false)
    private WallOfFame walloffames;
}
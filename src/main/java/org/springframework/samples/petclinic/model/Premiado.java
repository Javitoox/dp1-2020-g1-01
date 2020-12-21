package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Table(name="premiados")
@Data
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
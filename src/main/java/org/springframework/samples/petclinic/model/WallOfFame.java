package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="walloffames")
@Getter
@Setter
public class WallOfFame {

    @Id
    @Column(name="fechawall")
    private String fechaWall;
}
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.LocalDate;
import javax.persistence.Id;

import lombok.Data;
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
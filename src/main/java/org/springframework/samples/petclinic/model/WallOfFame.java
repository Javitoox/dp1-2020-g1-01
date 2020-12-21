package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.LocalDate;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Table(name="walloffames")
@Data
public class WallOfFame {

    @Id
    @Column(name="fechawall")
    private LocalDate fechaWall;
}
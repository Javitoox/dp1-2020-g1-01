package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="tutores")
public class Tutor extends Usuario{
	
    @OneToMany(mappedBy = "tutores")
    private List<Alumno> alumnos;
    
}


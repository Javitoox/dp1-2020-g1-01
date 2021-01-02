package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name="profesores")
public class Profesor extends Usuario{
	
    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private Set<AsignacionProfesor> asignaciones = new HashSet<>();   

}

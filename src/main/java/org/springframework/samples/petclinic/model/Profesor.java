package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name="profesores")
public class Profesor extends Usuario{
	
  

}

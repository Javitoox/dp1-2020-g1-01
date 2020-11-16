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
@Table(name="tutor")
public class Tutor{
	
	@Id
	@Column(name="nicktutor", unique=true)
	private String nicktutor;
	
	@Column(name="contraseñatutor")
	private String contraseñatutor;
	
	@Column(name="dnitutor", unique=true)
	private String dnitutor;
	
	@Column(name="correoelectronicotutor")
	private String correoelectronicotutor;
	
	@Column(name="numtelefonotutor")
	private String numtelefonotutor;
	
    @OneToMany(mappedBy = "tutor")
    private List<Alumno> alumnos;
    
}


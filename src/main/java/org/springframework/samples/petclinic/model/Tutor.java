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
public class Tutor{
	
	@Id
	@Column(name="nick_tutor", unique=true)
	private String nickTutor;
	
	@Column(name="contraseya_tutor")
	private String contraseyaTutor;
	
	@Column(name="dni_tutor", unique=true)
	private String dniTutor;
	
	@Column(name="correo_electronico_tutor")
	private String correoElectronicoTutor;
	
	@Column(name="num_telefono_tutor")
	private String numTelefonoTutor;
	
}
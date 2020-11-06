package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name="alumno")
public class Alumno extends NamedEntity{
	
	@Column(name="dni")
	@NotEmpty
	private String dni;
	
	@Column(name="correo")
	@NotEmpty
	private String correo;
	
	@Column(name="telefono")
	@NotEmpty
	private String telefono;
	
	@Column(name="telefono2")
	@NotEmpty
	private String telefono2;
	
	@Column(name="direccion")
	@NotEmpty
	private String direccion;
	
	@Column(name="fechanacimiento")
	@NotEmpty
	private String fechanacimiento;
	

}

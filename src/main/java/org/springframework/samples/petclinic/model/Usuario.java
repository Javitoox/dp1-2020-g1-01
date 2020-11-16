package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
@Table(name="usuario")
@Inheritance(strategy=InheritanceType.JOINED)

public class Usuario{
	
	@Id
	@Column(name="nickusuario", unique=true)
	/*@Pattern(regexp = "^[a-zA-Z0-9]{5,20}$")*/
	private String nickusuario;
	
	@Column(name="contraseña")
	//Contraseña tiene que tener más de 8 caracteres, 2 números,
	//2 mayusculas, 2 simbolos de puntuacion y 2 minusculas.
	/*@Pattern(regexp = "^(?=(.*[0-9]){2})(?=(.*[!-\\.<-@_]){2})(?=(.*[A-Z]){2})(?=(.*[a-z]){2})\\S{8,100}$")*/
	@NotBlank
	@NotEmpty
	@NotNull
	private String contraseña;
	
	@Column(name="dniusuario", unique=true)
	@Pattern(regexp = "^[0-9]{8}[A-Z]$")
	@NotBlank
	@NotEmpty
	@NotNull
	private String dniusuario;
	
	@Column(name="nombrecompletousuario")
	@NotBlank
	@NotEmpty
	@NotNull
	private String nombrecompletousuario;
	
	@Column(name="correoelectronicousuario")
	@NotBlank
	@NotEmpty
	@NotNull
	private String correoelectronicousuario;
	
	@Column(name="numtelefonousuario")
	@NotBlank
	@Pattern(regexp = "^[0-9]{9}$")
	@NotNull
	@NotEmpty
	private String numtelefonousuario;
	
	@Column(name="direccionusuario")
	@NotBlank
	@NotEmpty
	@NotNull
	private String direccionusuario;
	
	@Column(name="fechanacimiento")
	@NotBlank
	@NotEmpty
	@NotNull
	private String fechanacimiento;
	
	@OneToMany(mappedBy = "usuario")
    private List<Solicitud> solicitudes;
}


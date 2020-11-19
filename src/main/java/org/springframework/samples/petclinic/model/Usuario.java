package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;


@Data
@MappedSuperclass
public class Usuario{
	
	@Id
	@Column(name="nick_usuario", unique=true)
	/*@Pattern(regexp = "^[a-zA-Z0-9]{5,20}$")*/
	private String nickUsuario;
	
	@Column(name="contraseya")
	//Contraseña tiene que tener más de 8 caracteres, 2 números,
	//2 mayusculas, 2 simbolos de puntuacion y 2 minusculas.
	/*@Pattern(regexp = "^(?=(.*[0-9]){2})(?=(.*[!-\\.<-@_]){2})(?=(.*[A-Z]){2})(?=(.*[a-z]){2})\\S{8,100}$")*/
	@NotBlank
	@NotEmpty
	@NotNull
	private String contraseya;
	
	@Column(name="dni_usuario", unique=true)
	@Pattern(regexp = "^[0-9]{8}[A-Z]$")
	@NotBlank
	@NotEmpty
	@NotNull
	private String dniUsuario;
	
	@Column(name="nombre_completo_usuario")
	@NotBlank
	@NotEmpty
	@NotNull
	private String nombreCompletoUsuario;
	
	@Column(name="correo_electronico_usuario")
	@NotBlank
	@NotEmpty
	@NotNull
	private String correoElectronicoUsuario;
	
	@Column(name="num_telefono_usuario")
	@NotBlank
	@Pattern(regexp = "^[0-9]{9}$")
	@NotNull
	@NotEmpty
	private String numTelefonoUsuario;
	
	@Column(name="direccion_usuario")
	@NotBlank
	@NotEmpty
	@NotNull
	private String direccionUsuario;
	
	@Column(name="fecha_nacimiento")
	@NotBlank
	@NotEmpty
	@NotNull
	private String fechaNacimiento;
	
}


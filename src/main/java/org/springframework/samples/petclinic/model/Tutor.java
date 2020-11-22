package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
@Table(name="tutores")
public class Tutor{
	
	@Id
	@Column(name="nick_usuario_tutor", unique=true)
	/*@Pattern(regexp = "^[a-zA-Z0-9]{5,20}$")*/
	private String nickUsuarioTutor;
	
	@Column(name="contraseya_tutor")
	//Contraseña tiene que tener más de 8 caracteres, 2 números,
	//2 mayusculas, 2 simbolos de puntuacion y 2 minusculas.
	/*@Pattern(regexp = "^(?=(.*[0-9]){2})(?=(.*[!-\\.<-@_]){2})(?=(.*[A-Z]){2})(?=(.*[a-z]){2})\\S{8,100}$")*/
	@NotBlank
	@NotEmpty
	@NotNull
	private String contraseyaTutor;
	
	@Column(name="dni_usuario_tutor", unique=true)
	@Pattern(regexp = "^[0-9]{8}[A-Z]$")
	@NotBlank
	@NotEmpty
	@NotNull
	private String dniUsuarioTutor;
	
	@Column(name="nombre_completo_usuario_tutor")
	@NotBlank
	@NotEmpty
	@NotNull
	private String nombreCompletoUsuarioTutor;
	
	@Column(name="correo_electronico_usuario_tutor")
	@NotBlank
	@NotEmpty
	@NotNull
	private String correoElectronicoUsuarioTutor;
	
	@Column(name="num_telefono_usuario_tutor")
	@NotBlank
	@Pattern(regexp = "^[0-9]{9}$")
	@NotNull
	@NotEmpty
	private String numTelefonoUsuarioTutor;
	
	@Column(name="direccion_usuario_tutor")
	@NotBlank
	@NotEmpty
	@NotNull
	private String direccionUsuarioTutor;
	
	@Column(name="fecha_nacimiento_tutor")
	@NotBlank
	@NotEmpty
	@NotNull
	private String fechaNacimientoTutor;

}


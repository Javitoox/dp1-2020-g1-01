package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Audited
@Getter
@Setter
@MappedSuperclass
public class Usuario{
	
	@Id
	@Column(name="nick_usuario")
	@Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "The username must have between 3 and 20 letters without accent or numbers (3 and 20 inclusive)")
	private String nickUsuario;
	
	@Column(name="contraseya")
//	@Pattern(regexp = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$", message = "\r\n" + 
//			"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase")
	@NotBlank(message = "Required field")
	private String contraseya;
	
	@Column(name="dni_usuario", unique=true)
	@Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "Incorrect format of nif")
	@NotBlank(message = "Required field")
	private String dniUsuario;
	
	@Column(name="nombre_completo_usuario")
	@Pattern(regexp = "^([Á-Úá-úa-zA-Z0-9] {0,1}[Á-Úá-úa-zA-Z0-9]*){1,70}$", message = "Incorrect format of full name")
	@NotBlank(message = "Required field")
	private String nombreCompletoUsuario;
	
	@Column(name="correo_electronico_usuario")
	@Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
			+ "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$", message = "Format must be: someone@someplace.es")
	@NotBlank(message = "Required field")
	private String correoElectronicoUsuario;
	
	@Column(name="num_telefono_usuario")
	@Pattern(regexp = "(6|7)[ -]*([0-9][ -]*){8}", message = "Incorrect format of first phone number")
	@NotBlank(message = "Required field")
	private String numTelefonoUsuario;
	
	@Column(name="num_telefono_usuario2")
	@Pattern(regexp = "(6|7)[ -]*([0-9][ -]*){8}", message = "Incorrect format of second phone number")
	private String numTelefonoUsuario2;
	
	@Column(name="direccion_usuario")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format of address")
	@NotBlank(message = "Required field")
	private String direccionUsuario;
	
	@Column(name="fecha_nacimiento")
	@NotNull(message = "Required field")
	@Past(message = "The date must be a past date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaNacimiento;	
}


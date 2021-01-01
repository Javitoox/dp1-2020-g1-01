package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Usuario{
	
	@Id
	@Column(name="nick_usuario")
	@Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "The username must have between 3 and 20 letters without accent or numbers (3 and 20 inclusive)")
	private String nickUsuario;
	
	@Column(name="contraseya")
	@Pattern(regexp = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$", message = "\r\n" + 
			"The password must be at least 8 to 16 characters long, at least one digit, at least one lowercase and at least one uppercase")
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
	@Pattern(regexp = "(6|7)[ -]*([0-9][ -]*){8}", message = "Incorrect format of first number phone")
	@NotBlank(message = "Required field")
	private String numTelefonoUsuario;
	
	@Column(name="num_telefono_usuario2")
	@Pattern(regexp = "(6|7)[ -]*([0-9][ -]*){8}", message = "Incorrect format of second number phone")
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

//	public Usuario() {
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((contraseya == null) ? 0 : contraseya.hashCode());
//		result = prime * result + ((correoElectronicoUsuario == null) ? 0 : correoElectronicoUsuario.hashCode());
//		result = prime * result + ((direccionUsuario == null) ? 0 : direccionUsuario.hashCode());
//		result = prime * result + ((dniUsuario == null) ? 0 : dniUsuario.hashCode());
//		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
//		result = prime * result + ((nickUsuario == null) ? 0 : nickUsuario.hashCode());
//		result = prime * result + ((nombreCompletoUsuario == null) ? 0 : nombreCompletoUsuario.hashCode());
//		result = prime * result + ((numTelefonoUsuario == null) ? 0 : numTelefonoUsuario.hashCode());
//		result = prime * result + ((numTelefonoUsuario2 == null) ? 0 : numTelefonoUsuario2.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Usuario other = (Usuario) obj;
//		if (contraseya == null) {
//			if (other.contraseya != null)
//				return false;
//		} else if (!contraseya.equals(other.contraseya))
//			return false;
//		if (correoElectronicoUsuario == null) {
//			if (other.correoElectronicoUsuario != null)
//				return false;
//		} else if (!correoElectronicoUsuario.equals(other.correoElectronicoUsuario))
//			return false;
//		if (direccionUsuario == null) {
//			if (other.direccionUsuario != null)
//				return false;
//		} else if (!direccionUsuario.equals(other.direccionUsuario))
//			return false;
//		if (dniUsuario == null) {
//			if (other.dniUsuario != null)
//				return false;
//		} else if (!dniUsuario.equals(other.dniUsuario))
//			return false;
//		if (fechaNacimiento == null) {
//			if (other.fechaNacimiento != null)
//				return false;
//		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
//			return false;
//		if (nickUsuario == null) {
//			if (other.nickUsuario != null)
//				return false;
//		} else if (!nickUsuario.equals(other.nickUsuario))
//			return false;
//		if (nombreCompletoUsuario == null) {
//			if (other.nombreCompletoUsuario != null)
//				return false;
//		} else if (!nombreCompletoUsuario.equals(other.nombreCompletoUsuario))
//			return false;
//		if (numTelefonoUsuario == null) {
//			if (other.numTelefonoUsuario != null)
//				return false;
//		} else if (!numTelefonoUsuario.equals(other.numTelefonoUsuario))
//			return false;
//		if (numTelefonoUsuario2 == null) {
//			if (other.numTelefonoUsuario2 != null)
//				return false;
//		} else if (!numTelefonoUsuario2.equals(other.numTelefonoUsuario2))
//			return false;
//		return true;
//	}
	
}


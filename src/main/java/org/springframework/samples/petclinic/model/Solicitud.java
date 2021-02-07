package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.validation.Valid;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solicitud {

	@Valid
	private Alumno alumno;

	@Valid
	private Tutor tutor;

	public String toJson() {
		LocalDate copiaFechaNacimiento = alumno.getFechaNacimiento();
		Gson json = new Gson();
		alumno.setFechaNacimiento(null);
		String jsonString = json.toJson(this);
		String result = jsonString.substring(0, jsonString.length() - 2) + ",\"fechaNacimiento\":\""
				+ copiaFechaNacimiento.toString() + "\"}" + "}";
		alumno.setFechaNacimiento(copiaFechaNacimiento);
		return result;
	}

	public String toJson2() {
		LocalDate copiaFechaNacimientoAlumno = alumno.getFechaNacimiento();
		LocalDate copiaFechaNacimientoTutor = tutor.getFechaNacimiento();
		Gson json = new Gson();
		alumno.setFechaNacimiento(null);
		tutor.setFechaNacimiento(null);
		String jsonString = json.toJson(this);
		System.out.println("El json original es : " + jsonString);
		String result = "{" + "\"alumno\"" + ":" + "{" + "\"nickUsuario\"" + ":" + "\"" + alumno.getNickUsuario().toString() + "\"" + "," + "\"contraseya\"" + ":" + "\"" + alumno.getContraseya().toString() + "\"" +
            "," + "\"dniUsuario\""+ ":"  + "\"" + alumno.getDniUsuario().toString() + "\"" + "," + "\"nombreCompletoUsuario\""+ ":"  + "\"" + alumno.getNombreCompletoUsuario().toString() + "\"" + "," +
            "\"correoElectronicoUsuario\"" + ":" + "\"" + alumno.getCorreoElectronicoUsuario().toString() + "\"" + "," + "\"numTelefonoUsuario\"" + ":" + "\"" + alumno.getNumTelefonoUsuario().toString() + "\"" + ","
            + "\"direccionUsuario\"" + ":" + "\"" + alumno.getDireccionUsuario().toString() + "\"" + "," + "\"fechaNacimiento\"" + ":" + "\"" + copiaFechaNacimientoAlumno.toString() + "\"" + "}" + "," + "\"tutor\"" +
            ":" + "{" + "\"nickUsuario\"" + ":" + "\"" + tutor.getNickUsuario().toString() + "\"" + "," + "\"contraseya\"" + ":" + "\"" + tutor.getContraseya().toString() + "\"" +
           "," + "\"dniUsuario\""+ ":"  + "\"" + tutor.getDniUsuario().toString() + "\"" + "," + "\"nombreCompletoUsuario\""+ ":"  + "\"" + tutor.getNombreCompletoUsuario().toString() + "\"" + "," +
            "\"correoElectronicoUsuario\"" + ":" + "\"" + tutor.getCorreoElectronicoUsuario().toString() + "\"" + "," + "\"numTelefonoUsuario\"" + ":" + "\"" + tutor.getNumTelefonoUsuario().toString() + "\"" + ","
            + "\"direccionUsuario\"" + ":" + "\"" + tutor.getDireccionUsuario().toString() + "\"" + "," + "\"fechaNacimiento\"" + ":" + "\"" + copiaFechaNacimientoAlumno.toString() + "\"" + "}" + "}";
        alumno.setFechaNacimiento(copiaFechaNacimientoAlumno);
		System.out.println("El json modificado es : " + result );
		return result;
	}
}

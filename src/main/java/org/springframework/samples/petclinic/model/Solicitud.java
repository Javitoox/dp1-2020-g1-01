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
		String result = jsonString.substring(0, jsonString.length() - 1) + ",\"fechaNacimiento\":\""
				+ copiaFechaNacimiento.toString() + "\"}";
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
		String result = jsonString.substring(0, jsonString.length() - 1) + ",\"alumno.fechNacimiento\":\""
				+ copiaFechaNacimientoAlumno.toString() + "\",\"tutor.fechaNacimiento\":\""
				+ copiaFechaNacimientoTutor.toString() + "\"}";
		alumno.setFechaNacimiento(copiaFechaNacimientoAlumno);
		return result;
	}
}
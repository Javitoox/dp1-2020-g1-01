package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="eventos")
@Getter
@Setter
public class Evento extends BaseEntity{
	
	@Column(name="title")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 40, message = "The maximum size is 40 characters")
	@NotBlank(message = "Required field")
	private String title;
	
	@Column(name="start")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@NotNull(message = "Required field")
	private LocalDate start;
	
	@Column(name="end")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate end;
	
	@Column(name="descripcion")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 300, message = "The maximum size is 300 characters")
	@NotBlank(message = "Required field")
	private String descripcion;
	
	private String color;
	
	@ManyToOne(optional=false)
    private TipoEvento tipo;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="evento")
	@JsonIgnore
    private Collection<Inscripcion> inscripciones;
	
	public String toJson() {
		LocalDate copiaStart = start;
		LocalDate copiaEnd = end;
		Gson json = new Gson();
		this.setStart(null);
		this.setEnd(null);
		String jsonString = json.toJson(this);
		String result = jsonString.substring(0, jsonString.length()-1)+",\"start\":\""+copiaStart.toString()+"\",\"end\":\""+copiaEnd.toString()+"\"}";
		this.setStart(copiaStart);
		this.setEnd(copiaEnd);
		return result;
	}

}

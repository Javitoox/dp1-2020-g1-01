package org.springframework.samples.tea.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="pagos")
public class Pago extends BaseEntity{

	@ManyToOne(optional=false)
	private TipoPago tipo;

	@Column(name="concepto")
	@NotEmpty(message = "Required field")
	private String concepto;

	@Column(name="fecha")
	private LocalDate fecha;

    @ManyToOne(optional=false)
    private Alumno alumnos;


    public String toJson() {
		LocalDate copiaFecha = getFecha();
		Integer copiaId = getId();
		String copiaTipo = getTipo().getTipo();
		Gson json = new Gson();
		this.setFecha(null);
		this.setTipo(null);

		String jsonString = json.toJson(this);

		String result = jsonString.substring(0, jsonString.length() - 1) + ",\"fecha\":\"" + copiaFecha.toString()  + "\",\"tipo\":{" + "\"tipo\"" + ":" + "\"" + "Bizum" + "\"" +  "}" + "}";
		this.setFecha(copiaFecha);
		System.out.println(result);
		return result;
	}

    public String toJson2() {
		LocalDate copiaFecha = getFecha();
		Integer copiaId = getId();
		String copiaTipo = getTipo().getTipo();
		Gson json = new Gson();
		this.setFecha(null);
		this.setTipo(null);

		String jsonString = json.toJson(this);

		String result = jsonString.substring(0, jsonString.length() - 1) + ",\"fecha\":\"" + copiaFecha.toString()  + "\",\"tipo\":{" + "\"tipo\"" + ":" + "\"" + "\"" +  "}" + "}";
		this.setFecha(copiaFecha);
		System.out.println(result);
		return result;
	}

    public String toJson3() {
		LocalDate copiaFecha = getFecha();
		LocalDate copiaFechaBaja = alumnos.getFechaBaja();
		Integer copiaId = getId();
		String copiaTipo = getTipo().getTipo();
		Gson json = new Gson();
		this.setFecha(null);
		this.setTipo(null);
		alumnos.setFechaBaja(null);

		String jsonString = json.toJson(this);

		String result =  "{" + "\"concepto\"" + ":"  + "\"" + "PRIMERA_MATRICULA" + "\""  + "," + "\"alumnos\"" + ":" + "{" + "\"nickUsuario\"" + ":" + "\"" + alumnos.getNickUsuario() + "\"" + ","  + "\"fechaBaja\"" + ":" + "\"" + copiaFechaBaja.toString() + "\"" + "}" +"," + "\"id\""+ ":"  + "30"  + ",\"fecha\":\"" + copiaFecha.toString() + "\",\"tipo\":{" + "\"tipo\"" + ":" + "\"" + "Bizum" + "\"" +  "}" + "}";
		this.setFecha(copiaFecha);
		alumnos.setFechaBaja(copiaFechaBaja);
		System.out.println(result);
		return result;
	}



}

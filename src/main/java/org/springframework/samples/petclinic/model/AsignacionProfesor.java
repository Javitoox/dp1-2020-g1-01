package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="asignaciones_profesor")
public class AsignacionProfesor {
	
	@EmbeddedId
	private AsignacionProfesorKey id;
	
	@ManyToOne(optional=false)
    @MapsId("nickProfesor")
    @JoinColumn(name = "nick_profesor")
	private Profesor profesor;
	
	@ManyToOne(optional=true)
    @MapsId("nombreGrupo")
    @JoinColumn(name = "nombre_grupo")
	private Grupo grupo;
	
	@Column(name="fecha_asignacion")
	private LocalDate fecha;

//	public AsignacionProfesor() {
//	}
//
//	public AsignacionProfesor(AsignacionProfesorKey id, Profesor profesor, Grupo grupo, LocalDate fecha) {
//		this.id = id;
//		this.profesor = profesor;
//		this.grupo = grupo;
//		this.fecha = fecha;
//	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
//		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((profesor == null) ? 0 : profesor.hashCode());
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
//		AsignacionProfesor other = (AsignacionProfesor) obj;
//		if (fecha == null) {
//			if (other.fecha != null)
//				return false;
//		} else if (!fecha.equals(other.fecha))
//			return false;
//		if (grupo == null) {
//			if (other.grupo != null)
//				return false;
//		} else if (!grupo.equals(other.grupo))
//			return false;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (profesor == null) {
//			if (other.profesor != null)
//				return false;
//		} else if (!profesor.equals(other.profesor))
//			return false;
//		return true;
//	}
//	

}

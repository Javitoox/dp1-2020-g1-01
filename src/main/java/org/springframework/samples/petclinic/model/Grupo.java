package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name="grupos")
public class Grupo {
	@Id
	@Column(name="nombre_grupo")
	@NotEmpty(message = "Required field")
	private String nombreGrupo;
	
	@ManyToOne
	@Valid
    private Curso cursos;
	
	@OneToMany(mappedBy = "grupo")
	@JsonIgnore
    private Set<AsignacionProfesor> asignaciones = new HashSet<>();

	public Grupo() {
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((asignaciones == null) ? 0 : asignaciones.hashCode());
//		result = prime * result + ((cursos == null) ? 0 : cursos.hashCode());
//		result = prime * result + ((nombreGrupo == null) ? 0 : nombreGrupo.hashCode());
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
//		Grupo other = (Grupo) obj;
//		if (asignaciones == null) {
//			if (other.asignaciones != null)
//				return false;
//		} else if (!asignaciones.equals(other.asignaciones))
//			return false;
//		if (cursos == null) {
//			if (other.cursos != null)
//				return false;
//		} else if (!cursos.equals(other.cursos))
//			return false;
//		if (nombreGrupo == null) {
//			if (other.nombreGrupo != null)
//				return false;
//		} else if (!nombreGrupo.equals(other.nombreGrupo))
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		return "Grupo [nombreGrupo=" + nombreGrupo + ", cursos=" + cursos + ", asignaciones=" + asignaciones + "]";
//	}

}

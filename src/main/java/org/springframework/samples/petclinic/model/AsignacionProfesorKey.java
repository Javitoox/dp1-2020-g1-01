package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AsignacionProfesorKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="nick_profesor")
	String nickProfesor;
	
	@Column(name="nombre_grupo") 
	String nombreGrupo;

	public AsignacionProfesorKey() {
		
	}
	
	public AsignacionProfesorKey(String nickProfesor, String nombreGrupo) {
		this.nickProfesor = nickProfesor;
		this.nombreGrupo = nombreGrupo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nickProfesor == null) ? 0 : nickProfesor.hashCode());
		result = prime * result + ((nombreGrupo == null) ? 0 : nombreGrupo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AsignacionProfesorKey other = (AsignacionProfesorKey) obj;
		if (nickProfesor == null) {
			if (other.nickProfesor != null)
				return false;
		} else if (!nickProfesor.equals(other.nickProfesor))
			return false;
		if (nombreGrupo == null) {
			if (other.nombreGrupo != null)
				return false;
		} else if (!nombreGrupo.equals(other.nombreGrupo))
			return false;
		return true;
	}

		
	
}

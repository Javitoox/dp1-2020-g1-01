package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name="profesores")
public class Profesor extends Usuario{
	
    @OneToMany(mappedBy = "profesor")
    private Set<AsignacionProfesor> asignaciones = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asignaciones == null) ? 0 : asignaciones.hashCode());
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
		Profesor other = (Profesor) obj;
		if (asignaciones == null) {
			if (other.asignaciones != null)
				return false;
		} else if (!asignaciones.equals(other.asignaciones))
			return false;
		return true;
	}

    

}

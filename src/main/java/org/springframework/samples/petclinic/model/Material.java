package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
<<<<<<< HEAD
import javax.persistence.ManyToOne;
=======
>>>>>>> parent of a2a68a14... Revert "Repository Tests"
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="materiales")
@Getter
@Setter
public class Material extends BaseEntity {
	
	@Column(name="nombre_material")
	private String nombreMaterial;
	
	@Column(name="fecha_subida")
	private LocalDate fechaSubida;
	
<<<<<<< HEAD
	@ManyToOne(optional=false)
    private TipoMaterial tipoMaterial;
	
	@ManyToOne(optional=false)
	private Profesor profesores;
=======
	
>>>>>>> parent of a2a68a14... Revert "Repository Tests"
}

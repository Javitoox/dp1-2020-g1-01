package org.springframework.samples.tea.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

	@ManyToOne(optional=false)
    private TipoMaterial tipoMaterial;

	@ManyToOne(optional=false)
	private Profesor profesores;
}

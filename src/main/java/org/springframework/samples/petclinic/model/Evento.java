package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="eventos")
@Data
public class Evento {
	
	@EmbeddedId
	private EventoId id;
	
	@Column(name="descripcion")
	@Pattern(regexp = "^[ a-zA-Z0-9á-úÁ-Ú,]+$", message = "Incorrect format")
	@Size(max = 300, message = "The maximum size is 300 characters")
	@NotBlank(message = "Required field")
	private String descripcion;
	
	@ManyToOne(optional=false)
    private TipoEvento tipo;
	
	@OneToMany(cascade=CascadeType.ALL)
    private Collection<Inscripcion> inscripciones;

}

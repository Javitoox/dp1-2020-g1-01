package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="eventos")
public class Evento {
	
	@Id
	@Column(name="id_evento")
	private Integer idEvento;
	
	@Column(name="nombre_evento")
	private String nombreEvento;
	
	@Column(name="fecha_evento")
	private String fechaEvento;
	
	@Column(name="descripcion_evento")
	private String descripcionEvento;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "evento_grupo", joinColumns = @JoinColumn(name = "id_evento"),
			inverseJoinColumns = @JoinColumn(name = "nombre_grupo"))
	private Set<Grupo> grupos;
	
	@ManyToOne
    @JoinColumn(name = "id")
    private Calendario calendarios;
}

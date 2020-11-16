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
@Table(name="evento")
public class Evento {
	
	@Id
	@Column(name="idevento")
	private Integer idevento;
	
	@Column(name="nombreevento")
	private String nombreevento;
	
	@Column(name="fechaevento")
	private String fechaevento;
	
	@Column(name="descripcionevento")
	private String descripcionevento;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "eventogrupo", joinColumns = @JoinColumn(name = "idevento"),
			inverseJoinColumns = @JoinColumn(name = "nombregrupo"))
	private Set<Grupo> grupos;
	
	@ManyToOne
    @JoinColumn(name = "id")
    private Calendario calendario;
}

package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="calendarios")
public class Calendario extends BaseEntity{
	@OneToMany(mappedBy = "calendario")
    private List<Evento> eventos;
}

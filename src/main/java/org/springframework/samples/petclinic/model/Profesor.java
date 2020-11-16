package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="profesor")
public class Profesor extends Usuario{
	@OneToMany(mappedBy = "profesor")
    private List<Material> materiales;
}

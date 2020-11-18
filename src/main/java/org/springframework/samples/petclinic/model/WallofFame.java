package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="wall_of_fames")
public class WallofFame extends BaseEntity{

	/*
	 * @OneToMany(mappedBy = "wall_of_fame") private List<Premiado> premiados;
	 */
}

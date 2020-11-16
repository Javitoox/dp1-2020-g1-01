package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="walloffame")
public class WallofFame extends BaseEntity{

	@OneToMany(mappedBy = "walloffame")
    private List<Premiado> premiados;
}

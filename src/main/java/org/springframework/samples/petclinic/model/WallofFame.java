package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="walloffames")
public class WallofFame extends BaseEntity{

	@OneToMany(mappedBy = "walloffames")
    private List<Premiado> premiados;	
}
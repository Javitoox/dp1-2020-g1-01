package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data 
@Table(name="materiales")
public class Material extends BaseEntity{
	
	@Enumerated(EnumType.STRING)
    @Column(name="tipo_material")
    private TipoMaterial tipoMaterial;

    @Column(name="nombre_material")
    private String nombreMaterial;    
 
}
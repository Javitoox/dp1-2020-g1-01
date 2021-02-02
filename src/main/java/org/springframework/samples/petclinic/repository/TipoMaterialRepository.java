package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TipoMaterial;

public interface TipoMaterialRepository extends CrudRepository<TipoMaterial, String> {

}

package org.springframework.samples.tea.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.tea.model.TipoMaterial;

public interface TipoMaterialRepository extends CrudRepository<TipoMaterial, String> {

}

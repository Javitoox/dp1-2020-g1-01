package org.springframework.samples.tea.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.tea.model.TipoEvento;

public interface TipoEventoRepository extends CrudRepository<TipoEvento, String>{

}

package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TipoPago;

public interface TipoPagoRepository extends CrudRepository<TipoPago, String>{

}

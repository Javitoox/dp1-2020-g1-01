package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TipoPago;
import org.springframework.samples.petclinic.repository.TipoPagoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoPagoService {
	
	private TipoPagoRepository tipoPagoRepository;
	
	@Autowired
	public TipoPagoService(TipoPagoRepository tipoPagoRepository) {
		this.tipoPagoRepository = tipoPagoRepository;
	}
	
	public TipoPago getType(String id) {
		return tipoPagoRepository.findById(id).orElse(null);
	}

}
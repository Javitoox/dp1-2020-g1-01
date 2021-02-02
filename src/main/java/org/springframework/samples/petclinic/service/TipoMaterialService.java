package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.TipoMaterial;
import org.springframework.samples.petclinic.repository.TipoMaterialRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoMaterialService {

	private TipoMaterialRepository tipoMaterialRepository;

	public TipoMaterialService(TipoMaterialRepository tipoMaterialRepository) {
		super();
		this.tipoMaterialRepository = tipoMaterialRepository;
	}
	
	public TipoMaterial findById(String id) {
		return tipoMaterialRepository.findById(id).get();
	}
}

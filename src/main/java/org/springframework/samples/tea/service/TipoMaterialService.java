package org.springframework.samples.tea.service;

import org.springframework.samples.tea.model.TipoMaterial;
import org.springframework.samples.tea.repository.TipoMaterialRepository;
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

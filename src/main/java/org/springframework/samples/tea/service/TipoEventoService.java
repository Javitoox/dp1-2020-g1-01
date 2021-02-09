package org.springframework.samples.tea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tea.model.TipoEvento;
import org.springframework.samples.tea.repository.TipoEventoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoEventoService {

	private TipoEventoRepository tipoEventoRepository;

	@Autowired
	public TipoEventoService(TipoEventoRepository tipoEventoRepository) {
		this.tipoEventoRepository = tipoEventoRepository;
	}

	public TipoEvento getType(String id) {
		return tipoEventoRepository.findById(id).orElse(null);
	}

}

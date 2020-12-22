package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.repository.PremiadoRepository;
import org.springframework.stereotype.Service;


@Service
public class PremiadoService {
	
	@Autowired
	private PremiadoRepository premiadoRepository;

	public List<Premiado> premiadosPorFecha(String fechaWall){
		return premiadoRepository.premiadosPorFecha(fechaWall);
	}
	
	public String obtenerUltimaSemana() {
		return premiadoRepository.lastWallOfFame();
	}
}

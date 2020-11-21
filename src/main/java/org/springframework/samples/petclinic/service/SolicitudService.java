package org.springframework.samples.petclinic.service;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
	private SolicitudRepository solicitudRepository;
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
	}
	
//	public Solicitud getSolicitud(String nickUsuario) {
//		return solicitudRepository.findByNick(nickUsuario);
//	}

	public Collection<Solicitud> getAllSolicitudes() {
	return solicitudRepository.findAll();
	}
}

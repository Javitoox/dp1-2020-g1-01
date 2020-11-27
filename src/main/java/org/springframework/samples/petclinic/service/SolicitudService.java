package org.springframework.samples.petclinic.service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
	private SolicitudRepository solicitudRepository;
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository) {
		this.solicitudRepository = solicitudRepository;
	}
	
	
	public List<Alumno> getAllSolicitudes() {
		return solicitudRepository.findStudentsNotAcceptedYet().stream().collect(Collectors.toList());
	}
	
	public void declineRequest(String nickUsuario) {
		solicitudRepository.deleteById(nickUsuario);
		
	}
	
//	public Solicitud getSolicitud(String nickUsuario) {
//		return solicitudRepository.findByNick(nickUsuario);
//	}

	/*public Collection<Solicitud> getAllSolicitudes() {
	return solicitudRepository.findAll();
	}
	
	public void insertByNick(String nickUsuario) {
		Solicitud s = new Solicitud();
		s.setNickUsuario(nickUsuario);
		solicitudRepository.save(s);
	}*/
}

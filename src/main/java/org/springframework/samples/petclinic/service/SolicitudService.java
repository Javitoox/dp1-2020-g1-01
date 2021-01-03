package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
	
	private SolicitudRepository solicitudRepository;
	private AlumnoService alumnoService;
	private TutorService tutorService;
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository, AlumnoService alumnoService, TutorService tutorService) {
		this.solicitudRepository = solicitudRepository;
		this.alumnoService = alumnoService;
		this.tutorService = tutorService;
	}
	
	public List<Alumno> getAllSolicitudes() {
		return solicitudRepository.findStudentsNotAcceptedYet();
	}
	
	@Transactional
	public void declineRequest(Alumno alumnoDenegado) throws DataAccessException{
		if(alumnoDenegado.getTutores() != null) {
			String nickTutor = alumnoDenegado.getTutores().getNickUsuario();
			alumnoService.deleteStudents(alumnoDenegado);
			List<Alumno> alumnosPorTutor = alumnoService.getAllMyStudents(nickTutor);
			if(alumnosPorTutor.size() < 1) {
				tutorService.delete(nickTutor);
			}
		}
	}
	
	@Transactional
	public void acceptRequest(Alumno student) throws DataAccessException{
		if(student.getTutores() != null) {
			tutorService.saveTutor(student.getTutores());
		}
		alumnoService.saveAlumno(student);
	}
	
	@Transactional
	public void saveRequest(Solicitud solicitud) throws DataAccessException{
		if(solicitud.getTutor() != null) tutorService.saveTutor(solicitud.getTutor());
		alumnoService.saveAlumno(solicitud.getAlumno());
	}
	
	public Tutor getTutor(String nickUsuario) {
		return tutorService.getTutor(nickUsuario);
	}
	
	public Alumno getAlumno(String nickUsuario) {
		return alumnoService.getAlumno(nickUsuario);
	}
	
}
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
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
			alumnoService.deleteStudent(alumnoDenegado.getNickUsuario());
			List<Alumno> alumnosPorTutor = alumnoService.getAllMyStudents(nickTutor);
			if(alumnosPorTutor.size() < 1) {
				tutorService.delete(nickTutor);
			} 
		}else {
			alumnoService.deleteStudent(alumnoDenegado.getNickUsuario());
		} 
	} 
	
	@Transactional
	public void acceptRequest(Alumno student) throws DataAccessException{
		student.setFechaMatriculacion(LocalDate.now());
		Tutor t = student.getTutores();
		
		if(t != null) {
			if(t.getFechaMatriculacion() == null) {
				 t.setFechaMatriculacion(LocalDate.now());
				 student.setTutores(t);
			}
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
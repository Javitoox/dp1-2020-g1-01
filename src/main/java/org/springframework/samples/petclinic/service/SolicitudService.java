package org.springframework.samples.petclinic.service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.AlumnoRepository;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.samples.petclinic.repository.TutorRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
	
	private SolicitudRepository solicitudRepository;
	private AlumnoRepository alumnoRepository;
	private TutorRepository tutorRepository;
	
	@Autowired
	public SolicitudService(SolicitudRepository solicitudRepository, AlumnoRepository alumnoRepository, TutorRepository tutorRepository) {
		this.solicitudRepository = solicitudRepository;
		this.alumnoRepository = alumnoRepository;
		this.tutorRepository = tutorRepository;
	}
	
	
	public List<Alumno> getAllSolicitudes() {
		return solicitudRepository.findStudentsNotAcceptedYet().stream().collect(Collectors.toList());
	}
	
	@Transactional
	public void declineRequest(String nickUsuario) throws DataAccessException{
		solicitudRepository.deleteById(nickUsuario);
	}
	
	@Transactional
	public void acceptRequest(Alumno student) throws DataAccessException{
		solicitudRepository.save(student);
	}
	
	@Transactional
	public void saveAlumno(Alumno alumno) throws DataAccessException{
		alumnoRepository.save(alumno);
	}
	
	public Tutor getTutor(String nickUsuario) {
		return tutorRepository.findByNick(nickUsuario);
	}
	
	@Transactional
	public void saveTutor(Tutor tutor) throws DataAccessException{
		tutorRepository.save(tutor);
	}
	
	@Transactional
	public void updateRequestTutor(Tutor tutorGuardado, Tutor tutor) throws DataAccessException{
		if (tutorGuardado == null) {
			tutor.setFechaSolicitud(LocalDate.now());
		}
	}
	
}
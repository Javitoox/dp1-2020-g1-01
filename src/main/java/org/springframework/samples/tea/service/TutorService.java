package org.springframework.samples.tea.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.Tutor;
import org.springframework.samples.tea.repository.TutorRepository;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

	private TutorRepository tutorRepository;

	@Autowired
	public TutorService(TutorRepository tutorRepository) {
		this.tutorRepository=tutorRepository;
	}

	public List<Tutor> getAllTutores() {
		return tutorRepository.findAll();
	}

	public Tutor getTutor(String nickUser) {
		return tutorRepository.findByNick(nickUser);
	}

	@Transactional
	public void saveTutor(Tutor tutor) throws DataAccessException{
		tutorRepository.save(tutor);
	}

	@Transactional
	public void delete(String nickTutor) throws DataAccessException{
		tutorRepository.deleteById(nickTutor);
	}

	public List<String> getTeacherByGroup(String nombreGrupo) {
		return tutorRepository.getTeacherByGroup(nombreGrupo);
	}
	public Tutor getTutorByIdOrNif(String nickUsuario, String nif) {
		return tutorRepository.findByNickAndNif(nickUsuario, nif);
	}
}

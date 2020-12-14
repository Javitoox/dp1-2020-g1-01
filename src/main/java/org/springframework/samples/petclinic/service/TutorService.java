package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Tutor;
import org.springframework.samples.petclinic.repository.TutorRepository;
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
	
	@Transactional
	public void delete(String nickTutor) throws DataAccessException{
		tutorRepository.deleteById(nickTutor);
	}
}

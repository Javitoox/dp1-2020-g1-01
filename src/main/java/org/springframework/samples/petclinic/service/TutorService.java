package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Tutor getTutor(String nickUsuario) {
		return tutorRepository.findByNick(nickUsuario);
	}
	
	public void insert(Tutor tutor) {
		tutorRepository.save(tutor);
	}
	
	public List<Tutor> getAllTutores() {
		return tutorRepository.findAll();
	}
	public void delete(String nickTutor) {
		tutorRepository.deleteById(nickTutor);
	}
}

package org.springframework.samples.petclinic.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.repository.PagoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagoService {
	
	PagoRepository pagoRepository;
	
	@Autowired
	public PagoService(PagoRepository pagoRepository) {
		this.pagoRepository = pagoRepository;
	}
	
	
	public List<Alumno> getStudentsPayment(){
    	return pagoRepository.findPagosByStudents();
	}
    
    
    
    
    
}



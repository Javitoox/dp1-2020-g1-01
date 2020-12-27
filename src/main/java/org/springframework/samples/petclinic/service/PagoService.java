package org.springframework.samples.petclinic.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.repository.PagoRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedGroupNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<Alumno> getStudentsNoPayment(){
    	return pagoRepository.findNoPagosByStudents();
	}
	
	@Transactional
	public void savePaid(Pago pago){
		
		pagoRepository.save(pago);
			
	}

    
    
    
    
    
}



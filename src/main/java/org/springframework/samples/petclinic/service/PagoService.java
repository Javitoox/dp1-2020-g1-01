package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.TipoPago;
import org.springframework.samples.petclinic.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoService {
	
	PagoRepository pagoRepository;
	private TipoPagoService tipoPagoService;
	
	@Autowired
	public PagoService(PagoRepository pagoRepository) {
		this.pagoRepository = pagoRepository;
	}
	
	public Set<String> getAllPayments(){
		return pagoRepository.allPagos();
	}
	
	public List<Alumno> getStudentsByPayment(String concepto){
    	return pagoRepository.findStudentsByPago(concepto);
	}
	
	public List<Alumno> getStudentsNoPayment(String concepto){
    	return pagoRepository.findStudentByNoPago(concepto);
	}
	
	public List<String> getNameStudentByNoPago(){
		return pagoRepository.findNameStudentByNoPago();
	}
	
	public List<String> getNoPaymentByStudent(String student){
    	return pagoRepository.findNoPaymentByStudent(student);
	}
	
	public List<Pago> getPaymentsByStudent(String student){
		return pagoRepository.findPaymentsByStudent(student);
	}
	
	@Transactional
	public void savePayment(Pago pago){
		
		pagoRepository.save(pago);
			
	}
	
	@Transactional
	public Boolean assignPago(Pago pago, String type) throws DataAccessException{
		TipoPago t = tipoPagoService.getType(type);
		if(t != null) {
			pago.setTipo(t);
			pagoRepository.save(pago);
			return true;
		}else {
			return false;
		}
	}
	


    
    
    
    
    
}



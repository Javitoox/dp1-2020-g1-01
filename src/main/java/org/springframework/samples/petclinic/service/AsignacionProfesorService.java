package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.repository.AsignacionProfesorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignacionProfesorService {
	
	AsignacionProfesorRepository asignacionRep;

	@Autowired
	public AsignacionProfesorService(AsignacionProfesorRepository asignacionRep) {
		this.asignacionRep = asignacionRep;
	}
	
	@Transactional(readOnly=true)
	public List<AsignacionProfesor> getAllAsignacionesByUser(String user){
		return asignacionRep.getAsignacionesByProfesor(user);
	}
	
	@Transactional(readOnly=true)
	public List<String> getFreeGroups(){
		return asignacionRep.getFreeGroups();
	}
	
	@Transactional
	public void saveAsignacion(AsignacionProfesor a) {
		asignacionRep.save(a);
	}
}

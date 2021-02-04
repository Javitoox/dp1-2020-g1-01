package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;
import org.springframework.samples.petclinic.repository.AsignacionProfesorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignacionProfesorService {
	
	private AsignacionProfesorRepository asignacionRep;

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
	public void saveAsignacion(AsignacionProfesor a) throws DataAccessException{
		asignacionRep.save(a);
	}
	
	@Transactional
	public void deleteAsignacion(AsignacionProfesorKey a) throws DataAccessException{
		asignacionRep.deleteById(a);
		
	}
	public AsignacionProfesor findAsignacionProfesor(AsignacionProfesorKey a) {
		return asignacionRep.findById(a).get();		
	}
	public List<String> findAsignacionProfesor(String a) {
		List<String> ap= asignacionRep.getAsignacionesByGroup(a);		
		return ap;
	}
}

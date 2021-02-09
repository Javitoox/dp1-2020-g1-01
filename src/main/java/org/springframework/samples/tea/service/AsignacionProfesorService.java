package org.springframework.samples.tea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.AsignacionProfesor;
import org.springframework.samples.tea.model.AsignacionProfesorKey;
import org.springframework.samples.tea.repository.AsignacionProfesorRepository;
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
	public List<String> findAsignacionesByGroup(String a) {
		return asignacionRep.getAsignacionesByGroup(a);
	}

}

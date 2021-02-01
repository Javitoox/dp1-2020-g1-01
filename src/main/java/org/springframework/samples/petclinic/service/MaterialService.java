package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MaterialService {
	
	private MaterialRepository materialRepository;

	@Autowired
	public MaterialService(MaterialRepository materialRepository) {
		super();
		this.materialRepository = materialRepository;
	}
	
	public List<Material> getMaterialPorProfesor(String nickProfesor){
		return materialRepository.getMaterialByProfesor(nickProfesor);		
	}

}

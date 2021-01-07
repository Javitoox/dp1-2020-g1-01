package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.WallOfFame;
import org.springframework.samples.petclinic.repository.WallOfFameRepository;
import org.springframework.stereotype.Service;

@Service
public class WallOfFameService {

	@Autowired
	private WallOfFameRepository wallOfFameRepository;
	
	public Optional<WallOfFame> getWallById(String fechaWall) {
		return wallOfFameRepository.findById(fechaWall);
	}
	
	public WallOfFame saveWallOfFame(WallOfFame w) {
		return wallOfFameRepository.save(w);
	}
	
	public void deleteWallOfFame(WallOfFame w) {
		wallOfFameRepository.delete(w);
	}
}
 
package org.springframework.samples.tea.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.tea.model.WallOfFame;

public interface WallOfFameRepository extends CrudRepository<WallOfFame, String>{


}

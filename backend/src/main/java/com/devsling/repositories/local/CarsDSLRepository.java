package com.devsling.repositories.local;

import java.util.List;

import com.devsling.models.local.Car;
import com.devsling.repositories.config.AbstractDSLRepository;

public interface CarsDSLRepository extends AbstractDSLRepository<Car, String> {

  List<String> findAllDistinctMake();

}

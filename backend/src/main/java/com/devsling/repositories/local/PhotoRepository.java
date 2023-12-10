package com.devsling.repositories.local;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsling.models.local.Car;
import com.devsling.models.local.Photo;
import com.devsling.repositories.config.AbstractRepository;

@Repository
public interface PhotoRepository extends AbstractRepository<Photo, String> {

  Optional<Photo> findTopByCar(Car car);

  Optional<Photo> findTopByCarId(String carId);

}

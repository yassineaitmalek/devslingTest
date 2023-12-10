package com.devsling.repositories.local;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsling.models.local.Car;
import com.devsling.models.local.Video;
import com.devsling.repositories.config.AbstractRepository;

@Repository
public interface VideoRepository extends AbstractRepository<Video, String> {

  Optional<Video> findTopByCar(Car car);

  Optional<Video> findTopByCarId(String carId);

}

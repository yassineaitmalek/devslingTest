package com.devsling.repositories.local;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsling.constants.FuelType;
import com.devsling.models.local.Car;
import com.devsling.repositories.config.AbstractRepository;

@Repository
public interface CarsRepository extends AbstractRepository<Car, String> {

  List<Car> findAllByFuelTypeAndPriceLessThanEqual(FuelType fuelType, Double maxPrice);

  @Query(value = "select distinct make from car", nativeQuery = true)
  List<String> findAllDistinctMake();

}

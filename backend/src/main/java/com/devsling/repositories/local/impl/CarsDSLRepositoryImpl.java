package com.devsling.repositories.local.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.devsling.models.local.Car;
import com.devsling.models.local.QCar;
import com.devsling.repositories.config.impl.BaseRepositoryImpl;
import com.devsling.repositories.local.CarsDSLRepository;

import lombok.Setter;

@Setter
@Repository
public class CarsDSLRepositoryImpl extends BaseRepositoryImpl<Car, String> implements CarsDSLRepository {

  public CarsDSLRepositoryImpl(EntityManager em) {
    super(Car.class, em);

  }

  private static final QCar qcar = QCar.car;

  @Override
  public List<String> findAllDistinctMake() {

    return getJpaQueryFactory()
        .select(qcar.make)
        .distinct()
        .from(qcar)
        .fetch();

  }

}
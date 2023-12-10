package com.devsling.specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.devsling.dto.CarSearchDTO;
import com.devsling.models.local.Car;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CarSpecification extends AbstractSpecification {

  private Specification<Car> hasMake(String make) {
    return (root, query, builder) -> builder.like(root.get("make"), like(make));

  }

  private Specification<Car> hasModel(String model) {
    return (root, query, builder) -> builder.like(root.get("model"), like(model));

  }

  private Specification<Car> hasPrice(Double price) {
    return (root, query, builder) -> builder.equal(root.get("price"), price);

  }

  private Specification<Car> hasMileage(Integer mileage) {
    return (root, query, builder) -> builder.equal(root.get("mileage"), mileage);

  }

  private Specification<Car> hasRegistrationDate(LocalDate registrationDate) {
    return (root, query, builder) -> builder.equal(root.get("registrationDate"), registrationDate);

  }

  private Specification<Car> hasFuelType(FuelType fuelType) {
    return (root, query, builder) -> builder.equal(root.get("fuelType"), fuelType);

  }

  private Specification<Car> hasTransmissionType(TransmissionType transmissionType) {
    return (root, query, builder) -> builder.equal(root.get("transmissionType"), transmissionType);

  }

  public Specification<Car> searchRequest(CarSearchDTO carSearchDTO) {

    return Arrays.asList(
        transformer(carSearchDTO.getMake(), CarSpecification::hasMake),
        transformer(carSearchDTO.getModel(), CarSpecification::hasModel),
        transformer(carSearchDTO.getMileage(), CarSpecification::hasMileage),
        transformer(carSearchDTO.getRegistrationDate(), CarSpecification::hasRegistrationDate),
        transformer(carSearchDTO.getFuelType(), CarSpecification::hasFuelType),
        transformer(carSearchDTO.getTransmissionType(), CarSpecification::hasTransmissionType),
        transformer(carSearchDTO.getPrice(), CarSpecification::hasPrice))
        .stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(Specification.where(distinct()), Specification::and);

  }

}

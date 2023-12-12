package com.devsling;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.devsling.models.local.Car;
import com.devsling.repositories.local.CarsRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
class CarsReposioryTest {

  @Autowired
  private CarsRepository carsRepository;

  private Car car1;

  private Car car2;

  private Car car3;

  @BeforeEach
  void setUp() {
    this.car1 = Car.builder()
        .fuelType(FuelType.DIESEL)
        .make("make1")
        .mileage(32)
        .price(266d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2016-05-05"))
        .model("model")
        .build();

    this.car2 = Car.builder()
        .fuelType(FuelType.ELECTRIC)
        .make("make2")
        .mileage(32)
        .price(2000d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2016-05-05"))
        .model("model")
        .build();

    this.car3 = Car.builder()
        .fuelType(FuelType.ELECTRIC)
        .make("make2")
        .mileage(32)
        .price(2000d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2016-05-05"))
        .model("model")
        .build();

    carsRepository.save(car1);
    carsRepository.save(car2);
    carsRepository.save(car3);

  }

  @Test
  void getCarsByFuelTypeAndMaxPrice() {

    FuelType fuelType = FuelType.DIESEL;
    Double price = 1000d;

    List<Car> result = carsRepository.findAllByFuelTypeAndPriceLessThanEqual(fuelType, price);

    assertTrue(result.contains(car1) && (result.size() == 1));
  }

  @Test
  void getAvailableMakes() {

    List<String> result = carsRepository.findAllDistinctMake();

    assertTrue(result.containsAll(Arrays.asList(car1.getMake(), car2.getMake())) && (result.size() == 2));

  }

}
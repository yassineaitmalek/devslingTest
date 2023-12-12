package com.devsling;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.devsling.dto.CarDTO;
import com.devsling.dto.CarSearchDTO;
import com.devsling.exception.config.ApiException;
import com.devsling.models.local.Car;
import com.devsling.repositories.local.CarsRepository;
import com.devsling.services.CarsService;
import com.devsling.services.FileService;
import com.devsling.specification.CarSpecification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CarsServiceTest {

  @Mock
  private CarsRepository carsRepository;

  @Mock
  private FileService fileService;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private CarsService carsService;

  @Test
  void getCars() {
    CarSearchDTO carSearchDTO = new CarSearchDTO();
    Pageable pageable = Pageable.unpaged();
    Specification<Car> specification = CarSpecification.searchRequest(carSearchDTO);

    when(carsRepository.findAll(eq(specification), eq(pageable))).thenReturn(Page.empty());
    Page<Car> result = carsService.getCars(carSearchDTO, pageable);

    assertNotNull(result);
    assertTrue(result.isEmpty());

  }

  @Test
  void createCarWithRightRegistrationDate() {
    CarDTO carDTO = CarDTO.builder()
        .fuelType(FuelType.DIESEL)
        .make("model")
        .mileage(32)
        .price(266d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2016-05-05"))
        .model("model")
        .build();

    Car car = Car.builder()
        .fuelType(FuelType.DIESEL)
        .make("model")
        .mileage(32)
        .price(266d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2016-05-05"))
        .model("model")
        .build();

    when(modelMapper.map(carDTO, Car.class)).thenReturn(car);

    when(carsRepository.save(car)).thenReturn(car);

    Optional<Car> savedCar = carsService.createCar(carDTO);

    assertTrue(savedCar.isPresent());

  }

  @Test
  void createCarWithInvalidRegistrationDate() {
    CarDTO carDTO = CarDTO.builder()
        .fuelType(FuelType.DIESEL)
        .make("model")
        .mileage(32)
        .price(266d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2014-05-05"))
        .model("model")
        .build();

    Car car = Car.builder()

        .fuelType(FuelType.DIESEL)
        .make("model")
        .mileage(32)
        .price(266d)
        .transmissionType(TransmissionType.AUTOMATIC)
        .registrationDate(LocalDate.parse("2014-05-05"))
        .model("model")
        .build();

    when(modelMapper.map(carDTO, Car.class)).thenReturn(car);

    assertThrows(ApiException.class, () -> {
      carsService.createCar(carDTO);
    });

  }

}
package com.devsling.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.devsling.validation.CarDTOValidator;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@CarDTOValidator
@AllArgsConstructor
@NoArgsConstructor
public class CarSearchDTO {

  @Schema(description = "Make of the car")
  private String make;

  @Schema(description = "Model of the car")
  private String model;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "Registration date of the car")
  private LocalDate registrationDate;

  @Schema(description = "Price of the car")
  private Double price;

  @Schema(description = "Fuel type of the car")
  private FuelType fuelType;

  @Schema(description = "Mileage of the car")
  private Integer mileage;

  @Schema(description = "Transmisson type of the car")
  private TransmissionType transmissionType;

}

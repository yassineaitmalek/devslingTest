package com.devsling.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

  @NotNull
  @NotEmpty
  private String make;

  @NotNull
  @NotEmpty
  private String model;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate registrationDate;

  @NotNull
  private Double price;

  @NotNull
  private FuelType fuelType;

  @NotNull
  private Integer mileage;

  @NotNull
  private TransmissionType transmissionType;

  @NotNull
  private MultipartFile photoFile;

  @NotNull
  private MultipartFile videoFile;

}

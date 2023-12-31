package com.devsling.models.local;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.devsling.models.config.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Car extends BaseEntity {

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

  @Enumerated(EnumType.STRING)
  @Schema(description = "Fuel type of the car")
  private FuelType fuelType;

  @Schema(description = "Mileage of the car")
  private Integer mileage;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Transmisson type of the car")
  private TransmissionType transmissionType;

  @Schema(description = "photo of the car")
  @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
  private Photo photo;

  @Schema(description = "Video of the car")
  @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
  private Video video;
}
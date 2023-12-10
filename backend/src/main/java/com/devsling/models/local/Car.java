package com.devsling.models.local;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;
import com.devsling.models.config.BaseEntity;

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

  private String make;

  private String model;

  private LocalDate registrationDate;

  private Double price;

  private FuelType fuelType;

  private Integer mileage;

  private TransmissionType transmissionType;

  @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
  private Photo photo;

  @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
  private Video video;
}
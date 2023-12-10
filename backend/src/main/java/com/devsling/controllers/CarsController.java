package com.devsling.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.devsling.constants.FuelType;
import com.devsling.controllers.config.AbstractController;
import com.devsling.controllers.config.ApiDataResponse;
import com.devsling.dto.CarDTO;
import com.devsling.dto.CarSearchDTO;
import com.devsling.dto.FileDTO;
import com.devsling.models.local.Car;
import com.devsling.services.CarsService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarsController implements AbstractController {

  private final CarsService carsService;

  @GetMapping
  public ResponseEntity<ApiDataResponse<Page<Car>>> getCars(@ParameterObject @ModelAttribute CarSearchDTO carSearchDTO,
      @ParameterObject Pageable pageable) {

    return ok(carsService.getCars(carSearchDTO, pageable));
  }

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<ApiDataResponse<Optional<Car>>> createCar(@Valid @ModelAttribute CarDTO carDTO) {

    return create(carsService.createCar(carDTO));
  }

  @GetMapping("/byFuelTypeAndMaxPrice")
  public ResponseEntity<ApiDataResponse<List<Car>>> getCarsByFuelTypeAndMaxPrice(
      @Valid @NotNull @RequestParam(required = true) FuelType fuelType,
      @Valid @NotNull @RequestParam(required = true) Double maxPrice) {
    return ok(carsService.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice));
  }

  @GetMapping("/availableMakes")
  public ResponseEntity<ApiDataResponse<List<String>>> getAvailableMakes() {
    return ok(carsService.getAvailableMakes());
  }

  @PutMapping(value = "/{carId}/photo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<ApiDataResponse<Optional<Car>>> updateCarPicture(
      @Valid @NotNull @NotEmpty @PathVariable String carId,
      @ModelAttribute FileDTO fileDTO) {

    return ok(carsService.updateCarPhoto(carId, fileDTO));
  }

  @PutMapping(value = "/{carId}/video", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<ApiDataResponse<Optional<Car>>> updateCarVideo(
      @Valid @NotNull @NotEmpty @PathVariable String carId,
      @ModelAttribute FileDTO fileDTO) {

    return ok(carsService.updateCarVideo(carId, fileDTO));
  }

  @GetMapping("/{carId}/photo/download")
  public ResponseEntity<byte[]> downloadPhoto(@Valid @NotNull @NotEmpty @PathVariable String carId) {

    return download(carsService.downloadCarPhoto(carId));
  }

  @GetMapping("/{carId}/video/download")
  public ResponseEntity<byte[]> downloadVideo(@Valid @NotNull @NotEmpty @PathVariable String carId) {

    return download(carsService.downloadVideoPhoto(carId));
  }

  @ResponseBody
  @GetMapping("/{carId}/video/stream")
  public ResponseEntity<byte[]> streamVideo(@Valid @NotNull @NotEmpty @PathVariable String carId,
      @RequestHeader(value = "Range", required = false) String range) {

    return partial(carsService.streamCarVideo(carId, range));
  }

}

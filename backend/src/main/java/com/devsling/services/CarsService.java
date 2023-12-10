package com.devsling.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.devsling.constants.FileExtension;
import com.devsling.constants.FuelType;
import com.devsling.controllers.config.ApiDownloadInput;
import com.devsling.controllers.config.ApiPartialInput;
import com.devsling.dto.CarDTO;
import com.devsling.dto.FileDTO;
import com.devsling.exception.config.ApiException;
import com.devsling.models.local.Car;
import com.devsling.models.local.Photo;
import com.devsling.models.local.Video;
import com.devsling.repositories.local.CarsDSLRepository;
import com.devsling.repositories.local.CarsRepository;
import com.devsling.utility.FileUtility;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class CarsService {

  private final CarsRepository carsRepository;

  private final CarsDSLRepository carsDSLRepository;

  private final FileService fileService;

  private final ModelMapper modelMapper;

  public Optional<Car> createCar(@Valid @NotNull CarDTO carDTO) {

    Car car = modelMapper.map(carDTO, Car.class);
    if (car.getRegistrationDate().getYear() < 2015) {
      throw new ApiException("you can not register this car because it is befare 2015");
    }
    Optional.ofNullable(carDTO.getPhotoFile())
        .filter(e -> FileExtension.getByValue(FileUtility.getExtention(e.getOriginalFilename())).getType().isImage())
        .ifPresent(e -> setPhotoForCar(e, car));

    Optional.ofNullable(carDTO.getVideoFile())
        .filter(e -> FileExtension.getByValue(FileUtility.getExtention(e.getOriginalFilename())).getType().isVideo())
        .ifPresent(e -> setVideoForCar(e, car));

    return Optional.of(carsRepository.save(car));
  }

  public void setPhotoForCar(@Valid @NotNull MultipartFile file, @Valid @NotNull Car car) {
    Photo photo = new Photo();
    fileService.uploadAttachement(file, photo);
    car.setPhoto(photo);
    photo.setCar(car);
  }

  public void setVideoForCar(@Valid @NotNull MultipartFile file, @Valid @NotNull Car car) {
    Video video = new Video();
    fileService.uploadAttachement(file, video);
    car.setVideo(video);
    video.setCar(car);
  }

  public List<Car> getCarsByFuelTypeAndMaxPrice(@Valid @NotNull FuelType fuelType, @Valid @NotNull Double maxPrice) {
    return carsRepository.findAllByFuelTypeAndPriceLessThanEqual(fuelType, maxPrice);
  }

  public List<String> getAvailableMakes() {
    return carsDSLRepository.findAllDistinctMake();
  }

  public Optional<Car> updateCarPhoto(@Valid @NotNull @NotEmpty String carId, @Valid @NotNull FileDTO fileDTO) {
    if (!FileExtension.getByValue(FileUtility.getExtention(fileDTO.getFile().getOriginalFilename())).getType()
        .isImage()) {
      throw new ApiException("the uploaded attachement is not an image");
    }
    return carsRepository.findById(carId)
        .map(e -> reSetPhotoForCar(fileDTO.getFile(), e))
        .orElse(Optional.empty());

  }

  public Optional<Car> updateCarVideo(@Valid @NotNull @NotEmpty String carId, @Valid @NotNull FileDTO fileDTO) {
    if (!FileExtension.getByValue(FileUtility.getExtention(fileDTO.getFile().getOriginalFilename())).getType()
        .isVideo()) {
      throw new ApiException("the uploaded attachement is not a video");
    }
    return carsRepository.findById(carId)
        .map(e -> reSetVideoForCar(fileDTO.getFile(), e))
        .orElse(Optional.empty());

  }

  public Optional<Car> reSetPhotoForCar(@Valid @NotNull MultipartFile file, @Valid @NotNull Car car) {

    fileService.deleteAttachement(car.getPhoto());
    setPhotoForCar(file, car);
    return Optional.of(carsRepository.save(car));

  }

  public Optional<Car> reSetVideoForCar(@Valid @NotNull MultipartFile file, @Valid @NotNull Car car) {

    fileService.deleteAttachement(car.getVideo());
    setVideoForCar(file, car);
    return Optional.of(carsRepository.save(car));

  }

  public ApiDownloadInput downloadCarPhoto(@Valid @NotNull @NotEmpty String carId) {
    return carsRepository.findById(carId)
        .map(Car::getPhoto)
        .map(fileService::downloadAttachement)
        .orElseThrow(() -> new ApiException("error downloading the photo of the car with id " + carId));

  }

  public ApiDownloadInput downloadVideoPhoto(@Valid @NotNull @NotEmpty String carId) {
    return carsRepository.findById(carId)
        .map(Car::getVideo)
        .map(fileService::downloadAttachement)
        .orElseThrow(() -> new ApiException("error downloading the video of the car with id " + carId));

  }

  public ApiPartialInput streamCarVideo(@Valid @NotNull @NotEmpty String carId, String httpRange) {
    return carsRepository.findById(carId)
        .map(Car::getVideo)
        .map(e -> fileService.streamFile(e, httpRange))
        .orElseThrow(() -> new ApiException("error downloading the photo of the car with id " + carId));

  }

}

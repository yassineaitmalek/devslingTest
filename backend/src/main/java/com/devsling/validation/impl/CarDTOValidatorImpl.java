package com.devsling.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.devsling.constants.FileExtension;
import com.devsling.dto.CarDTO;
import com.devsling.utility.FileUtility;
import com.devsling.validation.CarDTOValidator;

import java.util.Objects;
import java.util.stream.Stream;

public class CarDTOValidatorImpl implements ConstraintValidator<CarDTOValidator, CarDTO> {

	@Override
	public boolean isValid(CarDTO carDTO, ConstraintValidatorContext context) {

		return Stream
				.of(checkNull(carDTO), checkFuel(carDTO), checkTransmition(carDTO), checkPrice(carDTO), checkMileage(carDTO),
						checkModel(carDTO), checkMake(carDTO), checkRegistrationDate(carDTO), checkPhoto(carDTO),
						checkVideo(carDTO))
				.noneMatch(Boolean.TRUE::equals);

	}

	public boolean checkNull(CarDTO carDTO) {
		return Objects.isNull(carDTO);
	}

	public boolean checkFuel(CarDTO carDTO) {
		return !checkNull(carDTO) && Objects.isNull(carDTO.getFuelType());
	}

	public boolean checkTransmition(CarDTO carDTO) {
		return !checkNull(carDTO) && Objects.isNull(carDTO.getTransmissionType());
	}

	public boolean checkPrice(CarDTO carDTO) {
		return !checkNull(carDTO) && (Objects.isNull(carDTO.getPrice()) || carDTO.getPrice() < 0);
	}

	public boolean checkMileage(CarDTO carDTO) {
		return !checkNull(carDTO) && (Objects.isNull(carDTO.getMileage()) || carDTO.getMileage() < 0);
	}

	public boolean checkModel(CarDTO carDTO) {
		return !checkNull(carDTO) && (Objects.isNull(carDTO.getModel()) || carDTO.getModel().trim().isEmpty());
	}

	public boolean checkMake(CarDTO carDTO) {
		return !checkNull(carDTO) && (Objects.isNull(carDTO.getMake()) || carDTO.getMake().trim().isEmpty());
	}

	public boolean checkRegistrationDate(CarDTO carDTO) {
		return !checkNull(carDTO)
				&& (Objects.isNull(carDTO.getRegistrationDate()) || carDTO.getRegistrationDate().getYear() < 2015);
	}

	public boolean checkPhoto(CarDTO carDTO) {
		return !checkNull(carDTO) && (Objects.nonNull(carDTO.getPhotoFile()) && !FileExtension
				.getByValue(FileUtility.getExtention(carDTO.getPhotoFile().getOriginalFilename())).getType().isImage());
	}

	public boolean checkVideo(CarDTO carDTO) {
		return !checkNull(carDTO) && (Objects.nonNull(carDTO.getVideoFile()) && !FileExtension
				.getByValue(FileUtility.getExtention(carDTO.getVideoFile().getOriginalFilename())).getType().isVideo());
	}

}
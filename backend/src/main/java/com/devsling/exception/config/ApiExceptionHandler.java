package com.devsling.exception.config;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.devsling.controllers.config.AbstractController;
import com.devsling.controllers.config.ApiExceptionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler implements AbstractController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiExceptionResponse> handleExceptions(Exception e) {
    log.error(e.getMessage(), e);
    return internalException(e);
  }

  @ExceptionHandler(value = { ApiException.class })
  public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException e) {
    if (e.getCause() != null) {
      log.error(e.getMessage(), e);
    } else {
      log.error(e.getMessage());
    }
    return badRequest(e);

  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(ConstraintViolationException e) {
    log.error(e.getMessage());
    return badRequest(e);

  }

}

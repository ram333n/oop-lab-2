package com.prokopchuk.airportmanagement.exception.handler;

import com.prokopchuk.airportmanagement.exception.CommonException;
import com.prokopchuk.airportmanagement.exception.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorMessage> handleNotFoundException(CommonException e) {
    ErrorMessage errorMessage = e.getErrorMessage();

    return new ResponseEntity<>(errorMessage, errorMessage.getResponseCode());
  }

}

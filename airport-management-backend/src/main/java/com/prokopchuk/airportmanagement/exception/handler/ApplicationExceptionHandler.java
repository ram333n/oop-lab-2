package com.prokopchuk.airportmanagement.exception.handler;

import com.prokopchuk.airportmanagement.exception.CommonException;
import com.prokopchuk.airportmanagement.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorMessage> handleCommonException(CommonException e) {
    ErrorMessage errorMessage = e.getErrorMessage();

    return errorMessage.toResponseEntity();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException e) {
    String message = getValidationErrorMessage(e);
    String[] splitMessage = message.split(":");
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.BAD_REQUEST,
        splitMessage[0],
        splitMessage[1]
    );

    return errorMessage.toResponseEntity();
  }

  private String getValidationErrorMessage(MethodArgumentNotValidException e) {
    return e.getBindingResult().getFieldError().getDefaultMessage();
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorMessage> handleAccessDeniedException() {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.FORBIDDEN,
        "access_denied",
        "Access denied"
    );

    return errorMessage.toResponseEntity();
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ErrorMessage> handleUnhandledException() {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "internal_server_error",
        "An internal server error occurred"
    );

    return errorMessage.toResponseEntity();
  }

}

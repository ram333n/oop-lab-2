package com.prokopchuk.airportmanagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

  @JsonIgnore
  private final HttpStatus responseCode;

  private final String errorCode;
  private final String errorDescription;

}

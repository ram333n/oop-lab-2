package com.prokopchuk.airportmanagement.controller.dto.flight;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightForm {

  @NotBlank
  @Size(max = 255)
  private String departureFrom;

  @NotBlank
  @Size(max = 255)
  private String destination;

  @Future
  private LocalDateTime departureTime;

  private LocalDateTime arrivalTime;

}

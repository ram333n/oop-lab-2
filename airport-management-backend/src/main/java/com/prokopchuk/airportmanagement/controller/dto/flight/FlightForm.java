package com.prokopchuk.airportmanagement.controller.dto.flight;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotBlank(message = "departure_from_is_blank:Departure city can not be blank")
  @Size(max = 255, message = "departure_from_max_size_limit:Departure city max size is 255 characters")
  private String departureFrom;

  @NotBlank(message = "destination_is_blank:Destination city can not be blank")
  @Size(max = 255, message = "destination_max_size_limit:Destination city max size is 255 characters")
  private String destination;

  @NotNull(message = "departure_time_is_empty:Departure time can not be empty")
  @Future(message = "departure_time_is_not_in_future:Departure time must be in future")
  private LocalDateTime departureTime;

  @NotNull(message = "arrival_time_is_empty:Arrival time can not be empty")
  @Future(message = "arrival_time_is_not_in_future:Arrival time must be in future")
  private LocalDateTime arrivalTime;

}

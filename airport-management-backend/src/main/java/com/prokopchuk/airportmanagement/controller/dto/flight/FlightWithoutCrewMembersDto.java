package com.prokopchuk.airportmanagement.controller.dto.flight;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightWithoutCrewMembersDto {

  private Long id;
  private String departureFrom;
  private String destination;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;

}

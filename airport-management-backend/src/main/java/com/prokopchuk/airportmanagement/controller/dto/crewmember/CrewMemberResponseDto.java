package com.prokopchuk.airportmanagement.controller.dto.crewmember;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.model.enums.Position;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewMemberResponseDto {

  private Long id;
  private String name;
  private String surname;
  private Position position;

  @JsonIgnore // TODO: create DTO to avoid StackOverflowError
  private List<Flight> flights;

}

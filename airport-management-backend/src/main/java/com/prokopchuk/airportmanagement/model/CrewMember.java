package com.prokopchuk.airportmanagement.model;

import com.prokopchuk.airportmanagement.model.enums.Position;
import lombok.Data;

@Data
public class CrewMember {

  private Long id;
  private String name;
  private String surname;
  private Position position;

}

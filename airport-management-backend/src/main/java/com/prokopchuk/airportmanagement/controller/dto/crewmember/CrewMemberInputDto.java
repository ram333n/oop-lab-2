package com.prokopchuk.airportmanagement.controller.dto.crewmember;

import com.prokopchuk.airportmanagement.model.enums.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewMemberInputDto {

  @NotBlank
  @Size(max = 255)
  private String name;

  @NotBlank
  @Size(max = 255)
  private String surname;

  @NotNull
  private Position position;

}

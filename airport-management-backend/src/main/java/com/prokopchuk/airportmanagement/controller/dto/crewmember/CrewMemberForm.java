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
public class CrewMemberForm {

  @NotBlank(message = "name_is_blank:Name can not be blank")
  @Size(max = 255, message = "name_max_size_limit:Max size of name is 255 characters")
  private String name;

  @NotBlank(message = "surname_is_blank:Surname can not be blank")
  @Size(max = 255, message = "surname_max_size_limit:Max size of surname is 255 characters")
  private String surname;

  @NotNull(message = "position_is_empty:Position can not be empty")
  private Position position;

}

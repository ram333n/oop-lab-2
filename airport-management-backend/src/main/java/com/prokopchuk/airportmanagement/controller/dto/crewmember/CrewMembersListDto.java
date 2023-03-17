package com.prokopchuk.airportmanagement.controller.dto.crewmember;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewMembersListDto {

  private List<CrewMemberWithoutFlightsDto> crewMembers;

}

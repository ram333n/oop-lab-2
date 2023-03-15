package com.prokopchuk.airportmanagement.controller;

import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberInputDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberResponseDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMembersListDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberWithoutFlightsDto;
import com.prokopchuk.airportmanagement.exception.CrewMemberNotFoundException;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.service.CrewMemberService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrewMemberController {

  private final CrewMemberService crewMemberService;
  private final ModelMapper modelMapper;

  @Autowired
  public CrewMemberController(CrewMemberService crewMemberService,
                              ModelMapper modelMapper) {
    this.crewMemberService = crewMemberService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/crew-members")
  public CrewMembersListDto getListOfCrewMembers() {
    List<CrewMember> entities = crewMemberService.findAll();

    List<CrewMemberWithoutFlightsDto> dtos = entities.stream()
        .map(e -> modelMapper.map(e, CrewMemberWithoutFlightsDto.class))
        .toList();

    return new CrewMembersListDto(dtos);
  }

  @GetMapping("/crew-members/{crew-member-id}")
  public CrewMemberResponseDto getCrewMemberById(@PathVariable("crew-member-id") Long id) {
    Optional<CrewMember> crewMember = crewMemberService.findCrewMemberById(id);

    if (crewMember.isEmpty()) {
      throw new CrewMemberNotFoundException(
          String.format("Crew member with id %d not found", id) //TODO: handle it
      );
    }

    return modelMapper.map(crewMember.get(), CrewMemberResponseDto.class);
  }

  @PostMapping("/crew-members")
  @ResponseStatus(HttpStatus.CREATED)
  public CrewMemberResponseDto createCrewMember(@Valid @RequestBody CrewMemberInputDto data) {
    CrewMember toSave = modelMapper.map(data, CrewMember.class);
    CrewMember response = crewMemberService.saveCrewMember(toSave);

    return modelMapper.map(response, CrewMemberResponseDto.class);
  }

  @PutMapping("/crew-members/{crew-member-id}")
  public CrewMemberResponseDto updateCrewMember(@PathVariable("crew-member-id") Long id,
                                                @Valid @RequestBody CrewMemberInputDto data) {
    if (!crewMemberService.existsById(id)) {
      throw new CrewMemberNotFoundException(
          String.format("Crew member with id %d not found", id) //TODO: handle it
      );
    }

    CrewMember toUpdate = modelMapper.map(data, CrewMember.class);
    toUpdate.setId(id);

    CrewMember response = crewMemberService.updateCrewMember(toUpdate);

    return modelMapper.map(response, CrewMemberResponseDto.class);
  }

  @DeleteMapping("/crew-members/{crew-member-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCrewMember(@PathVariable("crew-member-id") Long id) {
    boolean isDeleted = crewMemberService.deleteCrewMemberById(id);

    if (!isDeleted) {
      throw new CrewMemberNotFoundException(
          String.format("Crew member with id %d not found", id) //TODO: handle it
      );
    }
  }

}

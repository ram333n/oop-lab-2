package com.prokopchuk.airportmanagement.controller;

import com.prokopchuk.airportmanagement.controller.dto.IdToLinkUpDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberForm;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberResponseDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMembersListDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberWithoutFlightsDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightWithoutCrewMembersDto;
import com.prokopchuk.airportmanagement.exception.NotFoundException;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.service.CrewMemberService;
import com.prokopchuk.airportmanagement.service.CrewMembersFlightsLinkService;
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
  private final CrewMembersFlightsLinkService linkService;
  private final ModelMapper modelMapper;

  @Autowired
  public CrewMemberController(CrewMemberService crewMemberService,
                              CrewMembersFlightsLinkService linkService,
                              ModelMapper modelMapper) {
    this.crewMemberService = crewMemberService;
    this.linkService = linkService;
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
    Optional<CrewMember> crewMemberOptional = crewMemberService.findCrewMemberById(id);

    if (crewMemberOptional.isEmpty()) {
      throw new NotFoundException(NotFoundException.CREW_MEMBER_NOT_FOUND);
    }

    return mapAndFetchFlights(crewMemberOptional.get());
  }

  @PostMapping("/crew-members")
  @ResponseStatus(HttpStatus.CREATED)
  public CrewMemberResponseDto createCrewMember(@Valid @RequestBody CrewMemberForm form) {
    CrewMember toSave = modelMapper.map(form, CrewMember.class);
    CrewMember response = crewMemberService.saveCrewMember(toSave);

    //we don't use mapAndFetchFlights(), because flights list is empty after creation
    return modelMapper.map(response, CrewMemberResponseDto.class);
  }

  @PutMapping("/crew-members/{crew-member-id}")
  public CrewMemberResponseDto updateCrewMember(@PathVariable("crew-member-id") Long id,
                                                @Valid @RequestBody CrewMemberForm form) {
    if (!crewMemberService.existsById(id)) {
      throw new NotFoundException(NotFoundException.CREW_MEMBER_NOT_FOUND);
    }

    CrewMember toUpdate = modelMapper.map(form, CrewMember.class);
    toUpdate.setId(id);

    CrewMember response = crewMemberService.updateCrewMember(toUpdate);

    return mapAndFetchFlights(response);
  }

  @DeleteMapping("/crew-members/{crew-member-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCrewMember(@PathVariable("crew-member-id") Long id) {
    boolean isDeleted = crewMemberService.deleteCrewMemberById(id);

    if (!isDeleted) {
      throw new NotFoundException(NotFoundException.CREW_MEMBER_NOT_FOUND);
    }
  }

  @PostMapping("/crew-members/{crew-member-id}/flights")
  @ResponseStatus(HttpStatus.CREATED)
  public CrewMemberResponseDto linkUpFlight(@PathVariable("crew-member-id") Long crewMemberId,
                                            @Valid @RequestBody IdToLinkUpDto flightId) {
    //existence validation is here
    linkService.linkUpCrewMemberAndFlight(crewMemberId, flightId.getIdToLink());

    Optional<CrewMember> crewMemberOptional = crewMemberService.findCrewMemberById(crewMemberId);

    return mapAndFetchFlights(crewMemberOptional.get());
  }

  @DeleteMapping("/crew-members/{crew-member-id}/flights/{flight-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unlinkUpFlight(@PathVariable("crew-member-id") Long crewMemberId,
                             @PathVariable("flight-id") Long flightId) {
    linkService.unlinkUpCrewMemberAndFlight(crewMemberId, flightId);
  }

  private CrewMemberResponseDto mapAndFetchFlights(CrewMember crewMember) {
    List<Flight> flightEntities
        = crewMemberService.findFlightsOfCrewMember(crewMember);
    List<FlightWithoutCrewMembersDto> flightDtos = flightEntities.stream()
        .map(e -> modelMapper.map(e, FlightWithoutCrewMembersDto.class))
        .toList();

    CrewMemberResponseDto resultDto
        = modelMapper.map(crewMember, CrewMemberResponseDto.class);
    resultDto.setFlights(flightDtos);

    return resultDto;
  }

}
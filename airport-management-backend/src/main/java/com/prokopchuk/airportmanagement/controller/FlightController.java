package com.prokopchuk.airportmanagement.controller;

import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberWithoutFlightsDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightResponseDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightWithoutCrewMembersDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightsListDto;
import com.prokopchuk.airportmanagement.exception.NotFoundException;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.service.CrewMembersFlightsLinkService;
import com.prokopchuk.airportmanagement.service.FlightService;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

  private final FlightService flightService;
  private final CrewMembersFlightsLinkService linkService;
  private final ModelMapper modelMapper;

  @Autowired
  public FlightController(FlightService flightService,
                          CrewMembersFlightsLinkService linkService,
                          ModelMapper modelMapper) {
    this.flightService = flightService;
    this.linkService = linkService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/flights")
  public FlightsListDto getListOfFlights() {
    List<Flight> entities = flightService.findAll();

    List<FlightWithoutCrewMembersDto> dtos = entities.stream()
        .map(e -> modelMapper.map(e, FlightWithoutCrewMembersDto.class))
        .toList();

    return new FlightsListDto(dtos);
  }

  @GetMapping("/flights/{flight-id}")
  public FlightResponseDto getFlightById(@PathVariable("flight-id") Long id) {
    Optional<Flight> flightOptional = flightService.findFlightById(id);

    if (flightOptional.isEmpty()) {
      throw new NotFoundException(NotFoundException.FLIGHT_NOT_FOUND);
    }

    return mapToResponseDto(flightOptional.get());
  }

  private FlightResponseDto mapToResponseDto(Flight flight) {
    List<CrewMember> crewMemberEntities = flightService.findCrewMembersOfFlight(flight);
    List<CrewMemberWithoutFlightsDto> crewMembersDtos = crewMemberEntities.stream()
        .map(e -> modelMapper.map(e, CrewMemberWithoutFlightsDto.class))
        .toList();

    FlightResponseDto resultDto = modelMapper.map(flight, FlightResponseDto.class);
    resultDto.setCrewMembers(crewMembersDtos);

    return resultDto;
  }

}

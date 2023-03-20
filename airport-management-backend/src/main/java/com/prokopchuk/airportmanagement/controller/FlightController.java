package com.prokopchuk.airportmanagement.controller;

import com.prokopchuk.airportmanagement.controller.dto.IdToLinkUpDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberWithoutFlightsDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightForm;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightResponseDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightWithoutCrewMembersDto;
import com.prokopchuk.airportmanagement.controller.dto.flight.FlightsListDto;
import com.prokopchuk.airportmanagement.exception.NotFoundException;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.service.CrewMembersFlightsLinkService;
import com.prokopchuk.airportmanagement.service.FlightService;
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

    return mapAndFetchCrewMembers(flightOptional.get());
  }

  @PostMapping("/flights")
  @ResponseStatus(HttpStatus.CREATED)
  public FlightResponseDto createFlight(@Valid @RequestBody FlightForm form) {
    Flight toSave = modelMapper.map(form, Flight.class);
    Flight response = flightService.saveFlight(toSave);

    //we don't use mapAndFetchCrewMembers(), because crew members list is empty after creation
    return modelMapper.map(response, FlightResponseDto.class);
  }

  @PutMapping("/flights/{flight-id}")
  public FlightResponseDto updateFlight(@PathVariable("flight-id") Long id,
                                        @Valid @RequestBody FlightForm form) {
    if (!flightService.existsById(id)) {
      throw new NotFoundException(NotFoundException.FLIGHT_NOT_FOUND);
    }

    Flight toUpdate = modelMapper.map(form, Flight.class);
    toUpdate.setId(id);

    Flight response = flightService.updateFlight(toUpdate);

    return mapAndFetchCrewMembers(response);
  }

  @DeleteMapping("/flights/{flight-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteFlight(@PathVariable("flight-id") Long id) {
    boolean isDeleted = flightService.deleteFlightById(id);

    if (!isDeleted) {
      throw new NotFoundException(NotFoundException.FLIGHT_NOT_FOUND);
    }
  }

  @PostMapping("/flights/{flight-id}/crew-members")
  @ResponseStatus(HttpStatus.CREATED)
  public FlightResponseDto linkUpCrewMember(@PathVariable("flight-id") Long flightId,
                                            @RequestBody IdToLinkUpDto crewMemberId) {
    //existence validation is here
    linkService.linkUpCrewMemberAndFlight(crewMemberId.getIdToLink(), flightId);

    Optional<Flight> flightOptional = flightService.findFlightById(flightId);

    return mapAndFetchCrewMembers(flightOptional.get());
  }

  @DeleteMapping("/flights/{flight-id}/crew-members/{crew-member-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unlinkUpCrewMember(@PathVariable("flight-id") Long flightId,
                                 @PathVariable("crew-member-id") Long crewMemberId) {
    linkService.unlinkUpCrewMemberAndFlight(crewMemberId, flightId);
  }

  private FlightResponseDto mapAndFetchCrewMembers(Flight flight) {
    List<CrewMember> crewMemberEntities = flightService.findCrewMembersOfFlight(flight);
    List<CrewMemberWithoutFlightsDto> crewMembersDtos = crewMemberEntities.stream()
        .map(e -> modelMapper.map(e, CrewMemberWithoutFlightsDto.class))
        .toList();

    FlightResponseDto resultDto = modelMapper.map(flight, FlightResponseDto.class);
    resultDto.setCrewMembers(crewMembersDtos);

    return resultDto;
  }

}
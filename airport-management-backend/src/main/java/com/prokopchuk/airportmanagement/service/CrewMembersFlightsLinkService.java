package com.prokopchuk.airportmanagement.service;

import com.prokopchuk.airportmanagement.exception.NotFoundException;
import com.prokopchuk.airportmanagement.exception.ValidException;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.repository.CrewMemberRepository;
import com.prokopchuk.airportmanagement.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrewMembersFlightsLinkService {

  private final CrewMemberRepository crewMemberRepository;
  private final FlightRepository flightRepository;

  @Autowired
  public CrewMembersFlightsLinkService(CrewMemberRepository crewMemberRepository,
                                       FlightRepository flightRepository) {
    this.crewMemberRepository = crewMemberRepository;
    this.flightRepository = flightRepository;
  }

  @Transactional
  public void linkUpCrewMemberAndFlight(Long crewMemberId, Long flightId) {
    CrewMember crewMember = crewMemberRepository
        .findById(crewMemberId)
        .orElseThrow(
            () -> new NotFoundException(NotFoundException.CREW_MEMBER_NOT_FOUND_TO_LINK_UP)
        );

    Flight flight = flightRepository
        .findById(flightId)
        .orElseThrow(
            () -> new NotFoundException(NotFoundException.FLIGHT_NOT_FOUND_TO_LINK_UP)
        );

    boolean linkExists = checkLinkExistence(crewMember, flight);

    if (linkExists) {
      throw new ValidException(ValidException.LINK_ALREADY_EXISTS);
    }

    flight.getCrewMembers().add(crewMember);
    crewMember.getFlights().add(flight);
  }

  @Transactional
  public void unlinkUpCrewMemberAndFlight(Long crewMemberId, Long flightId) {
    CrewMember crewMember = crewMemberRepository
        .findById(crewMemberId)
        .orElseThrow(
            () -> new NotFoundException(NotFoundException.CREW_MEMBER_NOT_FOUND_TO_UNLINK_UP)
        );

    Flight flight = flightRepository
        .findById(flightId)
        .orElseThrow(
            () -> new NotFoundException(NotFoundException.FLIGHT_NOT_FOUND_TO_UNLINK_UP)
        );

    flight.getCrewMembers().remove(crewMember);
    boolean isUnlinked = crewMember.getFlights().remove(flight);

    if (!isUnlinked) {
      throw new NotFoundException(NotFoundException.LINK_IS_ABSENT);
    }
  }

  private boolean checkLinkExistence(CrewMember crewMember, Flight flight) {
    return crewMember.getFlights().contains(flight);
  }

}

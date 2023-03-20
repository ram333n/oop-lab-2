package com.prokopchuk.airportmanagement.service;

import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.repository.CrewMemberRepository;
import com.prokopchuk.airportmanagement.repository.FlightRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightService {

  private final FlightRepository flightRepository;
  private final CrewMemberRepository crewMemberRepository;

  @Autowired
  public FlightService(FlightRepository flightRepository,
                       CrewMemberRepository crewMemberRepository) {
    this.flightRepository = flightRepository;
    this.crewMemberRepository = crewMemberRepository;
  }

  @Transactional
  public Flight saveFlight(Flight flight) {
    return flightRepository.save(flight);
  }

  public Optional<Flight> findFlightById(Long id) {
    return flightRepository.findById(id);
  }

  public boolean existsById(Long id) {
    return flightRepository.existsById(id);
  }

  public List<Flight> findAll() {
    return flightRepository.findAll();
  }

  public List<CrewMember> findCrewMembersOfFlight(Flight flight) {
    return crewMemberRepository.findCrewMembersByFlights(flight);
  }

  @Transactional
  public Flight updateFlight(Flight flight) {
    return flightRepository.save(flight);
  }

  @Transactional
  public boolean deleteFlightById(Long id) {
    return flightRepository.deleteFlightById(id) > 0L;
  }

}

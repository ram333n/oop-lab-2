package com.prokopchuk.airportmanagement.service;

import com.prokopchuk.airportmanagement.model.Flight;
import com.prokopchuk.airportmanagement.repository.FlightRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightService {

  private final FlightRepository flightRepository;

  @Autowired
  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Transactional
  public Flight saveFlight(Flight flight) {
    return flightRepository.save(flight);
  }

  public Optional<Flight> findFlightById(Long id) {
    return flightRepository.findById(id);
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

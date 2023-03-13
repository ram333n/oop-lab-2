package com.prokopchuk.airportmanagement.service;

import com.prokopchuk.airportmanagement.model.Flight;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

  public Flight saveFlight(Flight flight) {
    return flight;
  }

  public Optional<Flight> findFlightById(Long id) {
    return Optional.of(new Flight());
  }

  public Flight updateFlight(Flight flight) {
    return flight;
  }

  public boolean deleteFlightById(Long id) {
    return true;
  }

}

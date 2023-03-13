package com.prokopchuk.airportmanagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prokopchuk.airportmanagement.model.Flight;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class FlightServiceTest {

  private final FlightService flightService = new FlightService();

  @Test
  void saveFlightWorksProperly() {
    Flight toSave = new Flight();

    Flight result = flightService.saveFlight(toSave);

    assertNotNull(result);
  }

  @Test
  void findFlightByIdWorksProperly() {
    Optional<Flight> result = flightService.findFlightById(1L);

    assertTrue(result.isPresent());
  }

  @Test
  void updateFlightWorksProperly() {
    Flight toUpdate = new Flight();

    Flight result = flightService.updateFlight(toUpdate);

    assertNotNull(result);
  }

  @Test
  void deleteFlightByIdWorksProperly() {
    boolean isDeleted = flightService.deleteFlightById(1L);

    assertTrue(isDeleted);
  }

}
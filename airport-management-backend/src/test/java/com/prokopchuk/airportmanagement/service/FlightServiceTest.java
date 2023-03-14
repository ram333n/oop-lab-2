package com.prokopchuk.airportmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prokopchuk.airportmanagement.model.Flight;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@AutoConfigureTestDatabase
@SqlGroup({
    @Sql(scripts = "/clear.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/test-init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
class FlightServiceTest {

  @Autowired
  private FlightService flightService;

  @Test
  void saveFlightWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);

    Flight toSave = Flight.builder()
        .departureFrom("Kyiv")
        .destination("Zhytomyr")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Flight actual = flightService.saveFlight(toSave);

    assertEquals(4L, actual.getId());
    assertEquals("Kyiv", actual.getDepartureFrom());
    assertEquals("Zhytomyr", actual.getDestination());
    assertEquals(departureTime, actual.getDepartureTime());
    assertEquals(arrivalTime, actual.getArrivalTime());
  }

  @Test
  void findFlightByIdWhenProvidedExistentId() {
    //2023-05-23 23:53:00 - expected departure time
    //2023-05-24 16:09:00 - expected arrival time
    LocalDateTime expectedDepartureTime = LocalDateTime.of(2023, 5, 23, 23, 53);
    LocalDateTime expectedArrivalTime = LocalDateTime.of(2023, 5, 24, 16, 9);

    Optional<Flight> actual = flightService.findFlightById(1L);

    assertTrue(actual.isPresent());
    assertEquals("Kyiv", actual.get().getDepartureFrom());
    assertEquals("Krakow", actual.get().getDestination());
    assertEquals(expectedDepartureTime, actual.get().getDepartureTime());
    assertEquals(expectedArrivalTime, actual.get().getArrivalTime());
  }

  @Test
  void findFlightByIdWhenProvidedNonExistentId() {
    Optional<Flight> actual = flightService.findFlightById(0L);

    assertTrue(actual.isEmpty());
  }

  @Test
  void updateFlightWorksProperly() {
    LocalDateTime departureTime = LocalDateTime.now().plusHours(1L);
    LocalDateTime arrivalTime = LocalDateTime.now().plusHours(6L);

    Flight toUpdate = Flight.builder()
        .id(1L)
        .departureFrom("Lviv")
        .destination("Zhytomyr")
        .departureTime(departureTime)
        .arrivalTime(arrivalTime)
        .build();

    Flight actual = flightService.updateFlight(toUpdate);

    assertEquals(1L, actual.getId());
    assertEquals("Lviv", actual.getDepartureFrom());
    assertEquals("Zhytomyr", actual.getDestination());
    assertEquals(departureTime, actual.getDepartureTime());
    assertEquals(arrivalTime, actual.getArrivalTime());
  }

  @Test
  void deleteFlightByIdWhenProvidedExistentId() {
    boolean isDeleted = flightService.deleteFlightById(1L);

    assertTrue(isDeleted);
  }

  @Test
  void deleteFlightByIdWhenProvidedNonExistentId() {
    boolean isDeleted = flightService.deleteFlightById(0L);

    assertFalse(isDeleted);
  }

}
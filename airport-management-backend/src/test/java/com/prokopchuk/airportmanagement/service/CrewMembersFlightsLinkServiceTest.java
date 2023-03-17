package com.prokopchuk.airportmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import com.prokopchuk.airportmanagement.exception.NotFoundException;
import com.prokopchuk.airportmanagement.exception.ValidException;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@SqlGroup({
    @Sql(scripts = "/clear.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/test-init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
class CrewMembersFlightsLinkServiceTest {

  @Autowired
  private CrewMemberService crewMemberService;

  @Autowired
  private CrewMembersFlightsLinkService linkService;

  @Test
  void linkUpCrewMemberAndFlightWhenProvidedNonExistentCrewMemberId() {
    Exception e = assertThrows(NotFoundException.class,
        () -> linkService.linkUpCrewMemberAndFlight(0L, 1L));

    assertEquals("Unable to link up due crew member absence" , e.getMessage());
  }

  @Test
  void linkUpCrewMemberAndFlightWhenProvidedNonExistentFlightId() {
    Exception e = assertThrows(NotFoundException.class,
        () -> linkService.linkUpCrewMemberAndFlight(2L, 42L));

    assertEquals("Unable to link up due flight absence" , e.getMessage());
  }

  @Test
  void linkUpCrewMemberAndFlightWhenLinkAlreadyExists() {
    Exception e = assertThrows(ValidException.class,
        () -> linkService.linkUpCrewMemberAndFlight(3L, 1L));

    assertEquals("Crew member and flight already linked up" , e.getMessage());
  }

  @Test
  void linkUpCrewMemberAndFlightWorksProperly() {
    linkService.linkUpCrewMemberAndFlight(1L, 2L);

    CrewMember crewMember = crewMemberService.findCrewMemberById(1L).orElseThrow();
    List<Flight> flights = crewMemberService.findFlightsOfCrewMember(crewMember);

    assertEquals(3, flights.size());
    assertTrue(flights.stream().anyMatch(f -> Objects.equals(2L, f.getId())));
  }

  @Test
  void unlinkUpCrewMemberAndFlightWhenProvidedNonExistentCrewMemberId() {
    Exception e = assertThrows(NotFoundException.class,
        () -> linkService.unlinkUpCrewMemberAndFlight(42L, 1L));

    assertEquals("Unable to unlink up due crew member absence" , e.getMessage());
  }

  @Test
  void unlinkUpCrewMemberAndFlightWhenProvidedNonExistentFlightId() {
    Exception e = assertThrows(NotFoundException.class,
        () -> linkService.unlinkUpCrewMemberAndFlight(1L, 42L));

    assertEquals("Unable to unlink up due flight absence", e.getMessage());
  }

  @Test
  void unlinkUpCrewMemberAndFlightWhenLinkIsAbsent() {
    Exception e = assertThrows(NotFoundException.class,
        () -> linkService.unlinkUpCrewMemberAndFlight(4L, 2L));

    assertEquals("The link between these crew member and flight is absent", e.getMessage());
  }

  @Test
  void unlinkUpCrewMemberAndFlightWorksProperly() {
    linkService.unlinkUpCrewMemberAndFlight(1L, 1L);

    CrewMember crewMember = crewMemberService.findCrewMemberById(1L).orElseThrow();
    List<Flight> flights = crewMemberService.findFlightsOfCrewMember(crewMember);

    assertEquals(1, flights.size());
    assertFalse(flights.stream().anyMatch(f -> Objects.equals(1L, f.getId())));
  }

}
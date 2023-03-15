package com.prokopchuk.airportmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberResponseDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMemberWithoutFlightsDto;
import com.prokopchuk.airportmanagement.controller.dto.crewmember.CrewMembersListDto;
import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.enums.Position;
import com.prokopchuk.airportmanagement.service.CrewMemberService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@SqlGroup({
    @Sql(scripts = "/clear.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "/test-init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
class CrewMemberControllerTest {

  @Autowired
  private CrewMemberService crewMemberService;

  @Autowired
  private CrewMemberController crewMemberController;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void contextLoads() {
    assertNotNull(crewMemberService);
    assertNotNull(crewMemberController);
    assertNotNull(restTemplate);
  }

  @Test
  void getListOfCrewMembersWorksProperly() {
    ResponseEntity<CrewMembersListDto> response = restTemplate.getForEntity(
        "/crew-members",
        CrewMembersListDto.class
    );

    List<CrewMemberWithoutFlightsDto> crewMembers = response.getBody().getCrewMembers();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(4, crewMembers.size());
    assertTrue(crewMembers.stream().anyMatch(m -> Objects.equals("Roman", m.getName())));
  }

  @Test
  void getCrewMemberByIdWhenMemberExists() {
    ResponseEntity<CrewMemberResponseDto> response = restTemplate.getForEntity(
        "/crew-members/{crew-member-id}",
        CrewMemberResponseDto.class,
        1L
    );

    CrewMemberResponseDto crewMember = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1L, crewMember.getId());
    assertEquals("Roman", crewMember.getName());
    assertEquals("Prokopchuk", crewMember.getSurname());
    assertEquals(Position.PILOT, crewMember.getPosition());
  }

  @Test
  void getCrewMemberByIdWhenMemberNotExists() {
    ResponseEntity<CrewMemberResponseDto> response = restTemplate.getForEntity(
        "/crew-members/{crew-member-id}",
        CrewMemberResponseDto.class,
        0L
    );

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
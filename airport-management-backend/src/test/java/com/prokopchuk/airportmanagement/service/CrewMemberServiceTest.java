package com.prokopchuk.airportmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import com.prokopchuk.airportmanagement.model.CrewMember;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CrewMemberServiceTest {

  private final CrewMemberService crewMemberService = new CrewMemberService();

  @Test
  void saveCrewMemberWorksProperly() {
    CrewMember toSave = new CrewMember();

    CrewMember result = crewMemberService.saveCrewMember(toSave);

    assertNotNull(result);
  }

  @Test
  void findCrewMemberByIdWorksProperly() {
    Optional<CrewMember> result = crewMemberService.findCrewMemberById(1L);

    assertTrue(result.isPresent());
  }

  @Test
  void updateCrewMemberWorksProperly() {
    CrewMember toUpdate = new CrewMember();

    CrewMember result = crewMemberService.updateCrewMember(toUpdate);

    assertNotNull(result);
  }

  @Test
  void deleteCrewMemberByIdWorksProperly() {
    boolean isDeleted = crewMemberService.deleteCrewMemberById(1L);

    assertTrue(isDeleted);
  }

}
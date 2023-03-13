package com.prokopchuk.airportmanagement.service;

import com.prokopchuk.airportmanagement.model.CrewMember;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {

  public CrewMember saveCrewMember(CrewMember crewMember) {
    return crewMember;
  }

  public Optional<CrewMember> findCrewMemberById(Long id) {
    return Optional.of(new CrewMember());
  }

  public CrewMember updateCrewMember(CrewMember toUpdate) {
    return toUpdate;
  }

  public boolean deleteCrewMemberById(Long id) {
    return true;
  }

}

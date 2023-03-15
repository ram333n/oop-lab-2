package com.prokopchuk.airportmanagement.service;

import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.repository.CrewMemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrewMemberService {

  private final CrewMemberRepository crewMemberRepository;

  @Autowired
  public CrewMemberService(CrewMemberRepository crewMemberRepository) {
    this.crewMemberRepository = crewMemberRepository;
  }

  @Transactional
  public CrewMember saveCrewMember(CrewMember crewMember) {
    return crewMemberRepository.save(crewMember);
  }

  public Optional<CrewMember> findCrewMemberById(Long id) {
    return crewMemberRepository.findById(id);
  }

  public boolean existsById(Long id) {
    return crewMemberRepository.existsById(id);
  }

  public List<CrewMember> findAll() {
    return crewMemberRepository.findAll();
  }

  @Transactional
  public CrewMember updateCrewMember(CrewMember toUpdate) {
    return crewMemberRepository.save(toUpdate);
  }

  @Transactional
  public boolean deleteCrewMemberById(Long id) {
    return crewMemberRepository.deleteCrewMemberById(id) > 0L;
  }

}

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
public class CrewMemberService {

  private final CrewMemberRepository crewMemberRepository;
  private final FlightRepository flightRepository;

  @Autowired
  public CrewMemberService(CrewMemberRepository crewMemberRepository,
                           FlightRepository flightRepository) {
    this.crewMemberRepository = crewMemberRepository;
    this.flightRepository = flightRepository;
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

  public List<Flight> findFlightsOfCrewMember(CrewMember crewMember) {
    return flightRepository.findFlightsByCrewMembers(crewMember);
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

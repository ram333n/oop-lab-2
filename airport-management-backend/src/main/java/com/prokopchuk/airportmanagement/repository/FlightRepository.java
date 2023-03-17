package com.prokopchuk.airportmanagement.repository;

import com.prokopchuk.airportmanagement.model.CrewMember;
import com.prokopchuk.airportmanagement.model.Flight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

  long deleteFlightById(Long id);

  List<Flight> findFlightsByCrewMembers(CrewMember crewMember);

}

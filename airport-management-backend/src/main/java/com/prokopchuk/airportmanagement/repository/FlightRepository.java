package com.prokopchuk.airportmanagement.repository;

import com.prokopchuk.airportmanagement.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}

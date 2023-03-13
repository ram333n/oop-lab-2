package com.prokopchuk.airportmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "flights")
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "departure_from", nullable = false)
  private String departureFrom;

  @Column(name = "destination", nullable = false)
  private String destination;

  @Column(name = "departure_time", nullable = false)
  private LocalDateTime departureTime;

  @Column(name = "arrival_time", nullable = false)
  private LocalDateTime arrivalTime;

  @ManyToMany(mappedBy = "flights")
  private Set<CrewMember> crewMembers = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Flight flight = (Flight) o;

    return Objects.equals(id, flight.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}

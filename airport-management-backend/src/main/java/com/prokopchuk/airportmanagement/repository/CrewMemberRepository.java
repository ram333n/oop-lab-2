package com.prokopchuk.airportmanagement.repository;

import com.prokopchuk.airportmanagement.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

}

package com.prokopchuk.airportmanagement.controller;

import com.prokopchuk.airportmanagement.service.CrewMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrewMemberController {

  private final CrewMemberService crewMemberService;

  @Autowired
  public CrewMemberController(CrewMemberService crewMemberService) {
    this.crewMemberService = crewMemberService;
  }

  @GetMapping("/test")
  public String testEndpoint() {
    return "Hello";
  }

}

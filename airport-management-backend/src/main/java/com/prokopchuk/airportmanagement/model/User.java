package com.prokopchuk.airportmanagement.model;

import com.prokopchuk.airportmanagement.model.enums.UserRole;
import lombok.Data;

@Data
public class User {

  private String email;
  private UserRole role;

}

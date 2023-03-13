package com.prokopchuk.airportmanagement.model;

import com.prokopchuk.airportmanagement.model.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "email")
  private String email;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    return Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }

}

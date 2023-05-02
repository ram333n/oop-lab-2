package com.prokopchuk.airportmanagement.controller;

import com.prokopchuk.airportmanagement.controller.dto.UserDto;
import com.prokopchuk.airportmanagement.model.enums.UserRole;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping("/me")
  @PreAuthorize("hasAnyRole('DISPATCHER', 'ADMIN')")
  public UserDto getMe(Principal principal) {
    JwtAuthenticationToken jwt = (JwtAuthenticationToken) principal;
    String username = (String) jwt.getTokenAttributes().get("preferred_username");
    GrantedAuthority authority = extractRole(jwt.getAuthorities());

    return new UserDto(username, authority.toString().split("_")[1]);
  }

  private GrantedAuthority extractRole(Collection<? extends GrantedAuthority> authorities) {
    return authorities.stream()
        .filter(r -> List.of("ROLE_DISPATCHER", "ROLE_ADMIN").contains(r.toString()))
        .findAny()
        .orElseThrow();
  }

}

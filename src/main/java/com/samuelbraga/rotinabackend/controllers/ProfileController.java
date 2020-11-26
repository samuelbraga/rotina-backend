package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.profile.ProfileDTO;
import com.samuelbraga.rotinabackend.services.ProfileService;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

  private final ProfileService profileService;

  @Autowired
  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProfileDTO> list(HttpServletRequest request) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.profileService.listProfiles(userId);
  }
}

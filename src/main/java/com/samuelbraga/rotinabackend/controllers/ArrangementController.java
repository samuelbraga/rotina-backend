package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.arrangement.ArrangementDTO;
import com.samuelbraga.rotinabackend.dtos.arrangement.CreateArrangementDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.services.ArrangementService;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arrangements")
public class ArrangementController {

  private final ArrangementService arrangementService;

  @Autowired
  public ArrangementController(ArrangementService arrangementService) {
    this.arrangementService = arrangementService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ArrangementDTO create(
    HttpServletRequest request,
    @RequestBody @Valid CreateArrangementDTO createArrangementDTO
  ) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.arrangementService.createGroup(
        createArrangementDTO,
        userId
      );
  }

  @GetMapping(path = "/company/{companyId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ArrangementDTO> list(
    HttpServletRequest request,
    @PathVariable UUID companyId
  ) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.arrangementService.listGroupsByCompany(userId, companyId);
  }

  @PostMapping(path = "/company/{companyId}/users")
  @ResponseStatus(HttpStatus.OK)
  public List<UserDTO> linkGroupToUsers(
    HttpServletRequest request,
    @PathVariable UUID companyId,
    @RequestBody @Valid List<UUID> usersId
  ) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.arrangementService.linkGroupsToUsers(userId, companyId, usersId);
  }
}

package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.group.CreateGroupDTO;
import com.samuelbraga.rotinabackend.dtos.group.GroupDTO;
import com.samuelbraga.rotinabackend.services.GroupService;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GroupDTO create(
    HttpServletRequest request,
    @RequestBody @Valid CreateGroupDTO createGroupDTO
  ) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.groupService.createGroup(createGroupDTO, userId);
  }
}

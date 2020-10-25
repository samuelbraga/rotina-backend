package com.samuelbraga.rotinabackend.Modules.User.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.IService.ICreateUserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserControllerTests {
  
  private final MockMvc mockMvc;
  private final ObjectMapper mapper;

  @MockBean
  private ICreateUserService createUserService;

  @MockBean
  private ModelMapper modelMapper;

  @Autowired
  public UserControllerTests(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
    this.mapper = new ObjectMapper();
  }

  @Test
  public void itShouldBeReturnedStatusCreatedForANewUser() throws Exception {
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      "DEFAULT",
      UUID.randomUUID());

    this.mockMvc.perform(post("/users")
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(this.mapper.writeValueAsString(createUserDTO)))
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void itShouldBeReturnedStatusBadRequestWhenNoPassARequiredProperty() throws Exception {
    CreateUserDTO createUserDTO = new CreateUserDTO();    

    this.mockMvc.perform(post("/users")
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(this.mapper.writeValueAsString(createUserDTO)))
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

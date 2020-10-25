package com.samuelbraga.rotinabackend.Modules.Company.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.IService.ICreateCompanyService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CompanyController.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CompanyControllerTests {
  
  private final MockMvc mockMvc;
  private final ObjectMapper mapper;
  
  @MockBean
  private ICreateCompanyService createCompanyService;
  
  @MockBean
  private ModelMapper modelMapper;
  
  @Autowired
  public CompanyControllerTests(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
    this.mapper = new ObjectMapper();
  }

  @Test
  public void itShouldBeReturnedStatusCreatedForANewCompany() throws Exception {
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");

    this.mockMvc.perform(post("/companies")
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(this.mapper.writeValueAsString(createCompanyDTO)))
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }
  
  @Test
  public void itShouldBeReturnedBadRequestWhenNoPassNameOfCompany() throws Exception {
    this.mockMvc.perform(post("/companies")
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}

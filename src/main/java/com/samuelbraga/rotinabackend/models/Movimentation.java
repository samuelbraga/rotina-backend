package com.samuelbraga.rotinabackend.models;

import com.samuelbraga.rotinabackend.enums.TypeMovimentation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
public class Movimentation {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  private BigDecimal value;
  
  @Enumerated(EnumType.STRING)
  private TypeMovimentation type;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar date;
  
  private String description;
  
  @ManyToOne
  private Account account;

  public Integer getId() {
    return id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public TypeMovimentation getType() {
    return type;
  }

  public void setType(TypeMovimentation type) {
    this.type = type;
  }

  public Calendar getDate() {
    return date;
  }

  public void setDate(Calendar date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

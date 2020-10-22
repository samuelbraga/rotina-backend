package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.models.Account;
import com.samuelbraga.rotinabackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
  
  @Autowired
  private AccountRepository accountRepository;
  
  @GetMapping
  @RequestMapping("/create")
  public Account create() {
    Account account = new Account();
    account.setNumber("123");
    account.setHolder("Samuel");
    account.setAgency("1");

    accountRepository.save(account);
    return account;
  }

  @GetMapping
  @RequestMapping("/list")
  public List<Account> list() {
    return accountRepository.findAll();
  }

  @GetMapping
  @RequestMapping("/search")
  public Optional<Account> search(@RequestParam Integer id) {
    Optional<Account> account = accountRepository.findById(id);
    return account;
  }

  @GetMapping
  @RequestMapping("/update/holder")
  public Optional<Account> updateHolder(@RequestParam Integer id) {
    Optional<Account> account = accountRepository.findById(id);
    account.ifPresent(acc -> {
      acc.setHolder("Mumuzinho");
      accountRepository.save(acc);
    });
    
    return account;
  }

  @GetMapping
  @RequestMapping("/delete")
  public void delete(@RequestParam Integer id) {
    accountRepository.deleteById(id);
  }
  
}

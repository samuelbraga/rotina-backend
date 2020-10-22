package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.enums.TypeMovimentation;
import com.samuelbraga.rotinabackend.models.Account;
import com.samuelbraga.rotinabackend.models.Movimentation;
import com.samuelbraga.rotinabackend.repository.AccountRepository;
import com.samuelbraga.rotinabackend.repository.MovimentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimentation")
public class MovimentationController {

  @Autowired
  private MovimentationRepository movimentationRepository;

  @Autowired
  private AccountRepository accountRepository;
  
  @RequestMapping("/create")
  public Movimentation create(@RequestParam Integer account_id) {
    Movimentation movimentation = new Movimentation();
    movimentation.setDate(Calendar.getInstance());
    movimentation.setDescription("Churrascaria");
    movimentation.setType(TypeMovimentation.SAIDA);
    movimentation.setValue(new BigDecimal(200.00));

    Account account = accountRepository.findById(account_id).get();    

    movimentation.setAccount(account);
    
    movimentationRepository.save(movimentation);
    return movimentation;    
  }

  @RequestMapping("/list")
  public List<Movimentation> list() {
    return movimentationRepository.findAll();
  }

  @RequestMapping("/search")
  public Movimentation search(@RequestParam Integer id) {
    return movimentationRepository.findById(id).get();
  }
}

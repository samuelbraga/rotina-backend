package com.samuelbraga.rotinabackend.repository;

import com.samuelbraga.rotinabackend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
  Optional<Account> findById(Integer id);
  void deleteById(Integer id);
}

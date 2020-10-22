package com.samuelbraga.rotinabackend.repository;

import com.samuelbraga.rotinabackend.models.Movimentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovimentationRepository extends JpaRepository<Movimentation, Integer> {
  Optional<Movimentation> findById(Integer id);
}

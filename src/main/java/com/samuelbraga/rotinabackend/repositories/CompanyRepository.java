package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findByName(String name);
}

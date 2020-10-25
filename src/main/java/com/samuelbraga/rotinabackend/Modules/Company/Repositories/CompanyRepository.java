package com.samuelbraga.rotinabackend.Modules.Company.Repositories;

import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findById(UUID id);
  Optional<Company> findByName(String name);
}
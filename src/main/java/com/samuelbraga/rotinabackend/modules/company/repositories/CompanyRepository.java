package com.samuelbraga.rotinabackend.modules.company.repositories;

import com.samuelbraga.rotinabackend.modules.company.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findById(UUID id);
  Optional<Company> findByName(String name);
}

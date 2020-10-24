package com.samuelbraga.rotinabackend.Repository;

import com.samuelbraga.rotinabackend.Models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findById(UUID id);
  Optional<Company> findByName(String name);
}

package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Company;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findByName(String name);
}

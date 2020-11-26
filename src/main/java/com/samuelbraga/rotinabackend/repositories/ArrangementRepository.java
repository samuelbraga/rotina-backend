package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Arrangement;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrangementRepository
  extends JpaRepository<Arrangement, UUID> {
  Optional<Arrangement> findByNameAndCompany_Id(String name, UUID companyId);
}

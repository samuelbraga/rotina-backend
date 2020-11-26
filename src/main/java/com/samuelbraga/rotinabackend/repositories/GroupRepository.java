package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Groups;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, UUID> {
  Optional<Groups> findByName(String name);
}

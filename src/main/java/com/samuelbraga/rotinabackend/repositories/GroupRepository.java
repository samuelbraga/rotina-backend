package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Group;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, UUID> {
  Optional<Group> findByName(String name);
}

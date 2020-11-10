package com.samuelbraga.rotinabackend.modules.user.repositories;

import com.samuelbraga.rotinabackend.modules.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findById(UUID id);
  Optional<User> findByEmail(String email);
}

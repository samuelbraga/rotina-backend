package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
  Optional<Profile> findById(UUID profileId);
}

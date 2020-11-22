package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Profile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {}

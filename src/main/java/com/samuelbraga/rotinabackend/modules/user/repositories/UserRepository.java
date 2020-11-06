package com.samuelbraga.rotinabackend.modules.user.repositories;

import com.samuelbraga.rotinabackend.modules.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

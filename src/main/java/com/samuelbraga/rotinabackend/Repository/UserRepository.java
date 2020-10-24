package com.samuelbraga.rotinabackend.Repository;

import com.samuelbraga.rotinabackend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

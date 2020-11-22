package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}

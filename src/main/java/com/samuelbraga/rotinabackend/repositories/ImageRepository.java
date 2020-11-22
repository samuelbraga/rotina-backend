package com.samuelbraga.rotinabackend.repositories;

import com.samuelbraga.rotinabackend.models.Image;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, UUID> {}

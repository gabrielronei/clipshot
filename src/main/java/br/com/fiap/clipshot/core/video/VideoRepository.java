package br.com.fiap.clipshot.core.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {

    Optional<Video> findByIdAndUserId(UUID id, Long userId);
}

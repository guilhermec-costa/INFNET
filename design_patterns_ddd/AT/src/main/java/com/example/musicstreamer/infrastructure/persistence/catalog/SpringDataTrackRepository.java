package com.example.musicstreamer.infrastructure.persistence.catalog;

import com.example.musicstreamer.domain.catalog.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTrackRepository extends JpaRepository<Track, UUID> {
}

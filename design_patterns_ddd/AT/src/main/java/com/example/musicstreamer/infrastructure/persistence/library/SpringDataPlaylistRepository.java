package com.example.musicstreamer.infrastructure.persistence.library;

import com.example.musicstreamer.domain.library.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPlaylistRepository extends JpaRepository<Playlist, UUID> {

    Optional<Playlist> findByIdAndAccountId(UUID playlistId, UUID accountId);

    List<Playlist> findAllByAccountId(UUID accountId);
}

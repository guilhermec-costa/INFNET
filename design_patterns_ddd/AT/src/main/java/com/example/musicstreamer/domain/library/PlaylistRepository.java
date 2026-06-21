package com.example.musicstreamer.domain.library;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistRepository {

    Playlist save(Playlist playlist);

    Optional<Playlist> findByIdAndAccountId(UUID playlistId, UUID accountId);

    List<Playlist> findAllByAccountId(UUID accountId);
}

package com.example.musicstreamer.infrastructure.persistence.library;

import com.example.musicstreamer.domain.library.Playlist;
import com.example.musicstreamer.domain.library.PlaylistRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlaylistRepositoryAdapter implements PlaylistRepository {

    private final SpringDataPlaylistRepository repository;

    public PlaylistRepositoryAdapter(SpringDataPlaylistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Playlist save(Playlist playlist) {
        return repository.save(playlist);
    }

    @Override
    public Optional<Playlist> findByIdAndAccountId(UUID playlistId, UUID accountId) {
        return repository.findByIdAndAccountId(playlistId, accountId);
    }

    @Override
    public List<Playlist> findAllByAccountId(UUID accountId) {
        return repository.findAllByAccountId(accountId);
    }
}

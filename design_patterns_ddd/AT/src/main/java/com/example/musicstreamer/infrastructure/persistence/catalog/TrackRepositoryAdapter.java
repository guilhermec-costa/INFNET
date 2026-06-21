package com.example.musicstreamer.infrastructure.persistence.catalog;

import com.example.musicstreamer.domain.catalog.Track;
import com.example.musicstreamer.domain.catalog.TrackRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrackRepositoryAdapter implements TrackRepository {

    private final SpringDataTrackRepository repository;

    public TrackRepositoryAdapter(SpringDataTrackRepository repository) {
        this.repository = repository;
    }

    @Override
    public Track save(Track track) {
        return repository.save(track);
    }

    @Override
    public Optional<Track> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Track> findAllById(Iterable<UUID> ids) {
        return repository.findAllById(ids);
    }
}

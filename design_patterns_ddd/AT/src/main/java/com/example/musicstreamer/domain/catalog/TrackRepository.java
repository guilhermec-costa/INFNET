package com.example.musicstreamer.domain.catalog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrackRepository {

    Track save(Track track);

    Optional<Track> findById(UUID id);

    List<Track> findAllById(Iterable<UUID> ids);
}

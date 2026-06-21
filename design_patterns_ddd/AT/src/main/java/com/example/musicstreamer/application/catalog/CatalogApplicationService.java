package com.example.musicstreamer.application.catalog;

import com.example.musicstreamer.domain.catalog.Track;
import com.example.musicstreamer.domain.catalog.TrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CatalogApplicationService implements TrackCatalogUseCase {

    private final TrackRepository trackRepository;

    public CatalogApplicationService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track createTrack(String title, String artist, String album) {
        return trackRepository.save(Track.create(title, artist, album));
    }
}

package com.example.musicstreamer.application.catalog;

import com.example.musicstreamer.domain.catalog.Track;

public interface TrackCatalogUseCase {

    Track createTrack(String title, String artist, String album);
}

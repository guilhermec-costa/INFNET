package com.example.musicstreamer.application.library;

import com.example.musicstreamer.domain.catalog.Track;

import java.util.List;
import java.util.UUID;

public interface FavoriteTrackUseCase {

    void favoriteTrack(UUID accountId, UUID trackId);

    List<Track> listFavoriteTracks(UUID accountId);
}

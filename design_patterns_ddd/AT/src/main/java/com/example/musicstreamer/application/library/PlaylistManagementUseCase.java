package com.example.musicstreamer.application.library;

import com.example.musicstreamer.domain.library.Playlist;

import java.util.List;
import java.util.UUID;

public interface PlaylistManagementUseCase {

    Playlist createPlaylist(UUID accountId, String name);

    Playlist addTrackToPlaylist(UUID accountId, UUID playlistId, UUID trackId);

    List<Playlist> listPlaylists(UUID accountId);
}

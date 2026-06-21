package com.example.musicstreamer.api.account;

import com.example.musicstreamer.application.library.FavoriteTrackUseCase;
import com.example.musicstreamer.application.library.PlaylistManagementUseCase;
import com.example.musicstreamer.domain.catalog.Track;
import com.example.musicstreamer.domain.library.Playlist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts/{accountId}")
public class LibraryController {

    private final FavoriteTrackUseCase favoriteTrackUseCase;
    private final PlaylistManagementUseCase playlistManagementUseCase;

    public LibraryController(FavoriteTrackUseCase favoriteTrackUseCase, PlaylistManagementUseCase playlistManagementUseCase) {
        this.favoriteTrackUseCase = favoriteTrackUseCase;
        this.playlistManagementUseCase = playlistManagementUseCase;
    }

    @PostMapping("/favorites/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void favoriteTrack(@PathVariable UUID accountId, @PathVariable UUID trackId) {
        favoriteTrackUseCase.favoriteTrack(accountId, trackId);
    }

    @GetMapping("/favorites")
    public List<FavoriteTrackResponse> listFavorites(@PathVariable UUID accountId) {
        return favoriteTrackUseCase.listFavoriteTracks(accountId).stream()
                .map(FavoriteTrackResponse::from)
                .toList();
    }

    @PostMapping("/playlists")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistResponse createPlaylist(@PathVariable UUID accountId, @Valid @RequestBody CreatePlaylistRequest request) {
        return PlaylistResponse.from(playlistManagementUseCase.createPlaylist(accountId, request.name()));
    }

    @PostMapping("/playlists/{playlistId}/tracks/{trackId}")
    public PlaylistResponse addTrack(@PathVariable UUID accountId, @PathVariable UUID playlistId, @PathVariable UUID trackId) {
        return PlaylistResponse.from(playlistManagementUseCase.addTrackToPlaylist(accountId, playlistId, trackId));
    }

    @GetMapping("/playlists")
    public List<PlaylistResponse> listPlaylists(@PathVariable UUID accountId) {
        return playlistManagementUseCase.listPlaylists(accountId).stream()
                .map(PlaylistResponse::from)
                .toList();
    }

    public record CreatePlaylistRequest(
            @NotBlank(message = "O nome da playlist é obrigatório.")
            String name
    ) {
    }

    public record FavoriteTrackResponse(UUID id, String title, String artist, String album) {
        static FavoriteTrackResponse from(Track track) {
            return new FavoriteTrackResponse(track.id(), track.title(), track.artist(), track.album());
        }
    }

    public record PlaylistResponse(UUID id, String name, List<UUID> trackIds) {
        static PlaylistResponse from(Playlist playlist) {
            return new PlaylistResponse(playlist.id(), playlist.name(), playlist.trackIds());
        }
    }
}

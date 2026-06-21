package com.example.musicstreamer.api.catalog;

import com.example.musicstreamer.application.catalog.TrackCatalogUseCase;
import com.example.musicstreamer.domain.catalog.Track;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackCatalogUseCase trackCatalogUseCase;

    public TrackController(TrackCatalogUseCase trackCatalogUseCase) {
        this.trackCatalogUseCase = trackCatalogUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackResponse create(@Valid @RequestBody CreateTrackRequest request) {
        return TrackResponse.from(trackCatalogUseCase.createTrack(request.title(), request.artist(), request.album()));
    }

    public record CreateTrackRequest(
            @NotBlank(message = "O título da música é obrigatório.")
            String title,
            @NotBlank(message = "O artista é obrigatório.")
            String artist,
            @NotBlank(message = "O álbum é obrigatório.")
            String album
    ) {
    }

    public record TrackResponse(UUID id, String title, String artist, String album) {
        static TrackResponse from(Track track) {
            return new TrackResponse(track.id(), track.title(), track.artist(), track.album());
        }
    }
}

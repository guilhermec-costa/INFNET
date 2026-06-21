package com.example.musicstreamer.domain.library;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "playlist_tracks", joinColumns = @JoinColumn(name = "playlist_id"))
    @Column(name = "track_id", nullable = false)
    private List<UUID> trackIds = new ArrayList<>();

    protected Playlist() {
    }

    private Playlist(UUID id, UUID accountId, String name) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
    }

    public static Playlist create(UUID accountId, String name) {
        return new Playlist(UUID.randomUUID(), accountId, name);
    }

    public UUID id() {
        return id;
    }

    public UUID accountId() {
        return accountId;
    }

    public String name() {
        return name;
    }

    public List<UUID> trackIds() {
        return List.copyOf(trackIds);
    }

    public void addTrack(UUID trackId) {
        trackIds.add(trackId);
    }
}

package com.example.musicstreamer.domain;

import com.example.musicstreamer.application.library.LibraryApplicationService;
import com.example.musicstreamer.domain.account.Account;
import com.example.musicstreamer.domain.account.AccountRepository;
import com.example.musicstreamer.domain.catalog.Track;
import com.example.musicstreamer.domain.catalog.TrackRepository;
import com.example.musicstreamer.domain.library.Playlist;
import com.example.musicstreamer.domain.library.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryApplicationServiceTest {

    private LibraryApplicationService libraryApplicationService;
    private InMemoryAccountRepository accountRepository;
    private InMemoryTrackRepository trackRepository;
    private InMemoryPlaylistRepository playlistRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new InMemoryAccountRepository();
        trackRepository = new InMemoryTrackRepository();
        playlistRepository = new InMemoryPlaylistRepository();
        libraryApplicationService = new LibraryApplicationService(accountRepository, trackRepository, playlistRepository);
    }

    @Test
    void shouldFavoriteTrackAndAddItToPlaylist() {
        Account account = accountRepository.save(Account.create("Fernanda"));
        Track track = trackRepository.save(Track.create("Tempo Perdido", "Legião Urbana", "Dois"));

        libraryApplicationService.favoriteTrack(account.id(), track.id());
        Playlist playlist = libraryApplicationService.createPlaylist(account.id(), "Favoritas");
        Playlist updatedPlaylist = libraryApplicationService.addTrackToPlaylist(account.id(), playlist.id(), track.id());

        assertThat(libraryApplicationService.listFavoriteTracks(account.id()))
                .extracting(Track::id)
                .containsExactly(track.id());
        assertThat(updatedPlaylist.trackIds()).containsExactly(track.id());
    }

    private static class InMemoryAccountRepository implements AccountRepository {
        private final Map<UUID, Account> storage = new HashMap<>();

        @Override
        public Account save(Account account) {
            storage.put(account.id(), account);
            return account;
        }

        @Override
        public Optional<Account> findById(UUID id) {
            return Optional.ofNullable(storage.get(id));
        }
    }

    private static class InMemoryTrackRepository implements TrackRepository {
        private final Map<UUID, Track> storage = new HashMap<>();

        @Override
        public Track save(Track track) {
            storage.put(track.id(), track);
            return track;
        }

        @Override
        public Optional<Track> findById(UUID id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public List<Track> findAllById(Iterable<UUID> ids) {
            List<Track> tracks = new ArrayList<>();
            ids.forEach(id -> {
                if (storage.containsKey(id)) {
                    tracks.add(storage.get(id));
                }
            });
            return tracks;
        }
    }

    private static class InMemoryPlaylistRepository implements PlaylistRepository {
        private final Map<UUID, Playlist> storage = new HashMap<>();

        @Override
        public Playlist save(Playlist playlist) {
            storage.put(playlist.id(), playlist);
            return playlist;
        }

        @Override
        public Optional<Playlist> findByIdAndAccountId(UUID playlistId, UUID accountId) {
            Playlist playlist = storage.get(playlistId);
            if (playlist == null || !playlist.accountId().equals(accountId)) {
                return Optional.empty();
            }
            return Optional.of(playlist);
        }

        @Override
        public List<Playlist> findAllByAccountId(UUID accountId) {
            return storage.values().stream().filter(playlist -> playlist.accountId().equals(accountId)).toList();
        }
    }
}

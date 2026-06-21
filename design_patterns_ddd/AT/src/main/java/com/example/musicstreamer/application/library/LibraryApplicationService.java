package com.example.musicstreamer.application.library;

import com.example.musicstreamer.api.shared.NotFoundException;
import com.example.musicstreamer.domain.account.Account;
import com.example.musicstreamer.domain.account.AccountRepository;
import com.example.musicstreamer.domain.catalog.Track;
import com.example.musicstreamer.domain.catalog.TrackRepository;
import com.example.musicstreamer.domain.library.Playlist;
import com.example.musicstreamer.domain.library.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LibraryApplicationService implements FavoriteTrackUseCase, PlaylistManagementUseCase {

    private final AccountRepository accountRepository;
    private final TrackRepository trackRepository;
    private final PlaylistRepository playlistRepository;

    public LibraryApplicationService(AccountRepository accountRepository, TrackRepository trackRepository, PlaylistRepository playlistRepository) {
        this.accountRepository = accountRepository;
        this.trackRepository = trackRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void favoriteTrack(UUID accountId, UUID trackId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
        ensureTrackExists(trackId);
        account.addFavoriteTrack(trackId);
        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Track> listFavoriteTracks(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
        return trackRepository.findAllById(account.favoriteTrackIds());
    }

    @Override
    public Playlist createPlaylist(UUID accountId, String name) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
        return playlistRepository.save(Playlist.create(accountId, name));
    }

    @Override
    public Playlist addTrackToPlaylist(UUID accountId, UUID playlistId, UUID trackId) {
        ensureTrackExists(trackId);
        Playlist playlist = playlistRepository.findByIdAndAccountId(playlistId, accountId)
                .orElseThrow(() -> new NotFoundException("Playlist não encontrada."));
        playlist.addTrack(trackId);
        return playlistRepository.save(playlist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Playlist> listPlaylists(UUID accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada."));
        return playlistRepository.findAllByAccountId(accountId);
    }

    private void ensureTrackExists(UUID trackId) {
        trackRepository.findById(trackId)
                .orElseThrow(() -> new NotFoundException("Música não encontrada."));
    }
}

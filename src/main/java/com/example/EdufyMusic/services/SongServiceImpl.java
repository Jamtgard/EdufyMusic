package com.example.EdufyMusic.services;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.clients.GenreClient;
import com.example.EdufyMusic.clients.ThumbClient;
import com.example.EdufyMusic.clients.UserClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.*;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.DTO.requests.SongsByGenreDTORequest;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.AlbumTrack;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.models.enums.MediaType;
import com.example.EdufyMusic.repositories.AlbumTrackRepository;
import com.example.EdufyMusic.repositories.SongRepository;
import com.example.EdufyMusic.utilities.MicroMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

// ED-74-SJ
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    // ED-309-SJ
    private final AlbumTrackRepository albumTrackRepository;

    // ED-237-SJ
    private final AlbumService albumService;

    // ED-235-SJ
    private final CreatorClient creatorClient;
    private final GenreClient genreClient;
    private final ThumbClient thumbClient;
    private final UserClient userClient;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, AlbumTrackRepository albumTrackRepository,AlbumService albumService, CreatorClient creatorClient, GenreClient genreClient, ThumbClient thumbClient, UserClient userClient)
    {
        this.songRepository = songRepository;
        this.albumTrackRepository = albumTrackRepository;
        this.albumService = albumService;
        this.creatorClient = creatorClient;
        this.genreClient = genreClient;
        this.thumbClient = thumbClient;
        this.userClient = userClient;
    }

    // ED-74-SJ
    @Override
    public SongResponseDTO getSongById(long id) {
        Song song = songRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Song", "id", id)
        );
        return SongResponseMapper.toDtoWithId(song);
    }

    // ED-49-SJ
    // ED-80-SJ reworked structure with new way of authentication.
    @Override
    public List<SongResponseDTO> findSongByTitle(String title, Authentication authentication) {

        List<String> roles = Roles.getRoles(authentication);
        boolean isAdmin = roles.contains("music_admin") || roles.contains("edufy_realm_admin");

        List<Song> songs = isAdmin
                ? songRepository.findByTitleContainingIgnoreCase(title)
                : songRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);

        MicroMethodes.validateListNotEmpty(songs, "List of Songs by title");

        return isAdmin
                ? SongResponseMapper.toDtoListWithId(songs)
                : SongResponseMapper.toDtoListNoId(songs);
    }

    // ED-80-SJ
    @Override
    public List<SongResponseDTO> getAllSongs(Authentication authentication) {

        List<String> roles = Roles.getRoles(authentication);
        boolean isAdmin = roles.contains("music_admin") || roles.contains("edufy_realm_admin");

        List<Song> songs = isAdmin
                ? songRepository.findAll()
                : songRepository.findAllByActiveTrue();

        MicroMethodes.validateListNotEmpty(songs, "List of all Songs");

        return isAdmin
                ? SongResponseMapper.toDtoListWithId(songs)
                : SongResponseMapper.toDtoListNoId(songs);
    }

    @Override
    @Transactional
    public List<SongResponseDTO> getUserHistory(Long userId) {
        List<Long> songsInUserHistory = songRepository.findSongIdsByUserIdInHistory(userId);

        if (songsInUserHistory.isEmpty()) { return Collections.emptyList();}

        return songsInUserHistory.stream()
                .map(SongResponseMapper::toDtoClientOnlyId)
                .collect(Collectors.toList());
    }

    // ED-237-SJ
    private List<Song> fetchSongsOrdered(List<Long> ids) {
        List<Song> fetched = songRepository.findAllById(ids);

        Map<Long, Song> byId = fetched.stream()
                .collect(Collectors.toMap(Song::getId, Function.identity()));

        return ids.stream()
                .map(byId::get)
                .filter(Objects::nonNull)
                .toList();
    }

    // ED-273-SJ
    @Override
    public List<SongResponseDTO> getSongsByGenre(Long genreId) {

        SongsByGenreDTORequest request = genreClient.getSongsByGenre(genreId, MediaType.SONG);
        List<Long> songIds = request != null
                ? request.getMediaIds()
                : Collections.emptyList();

        MicroMethodes.validateListNotEmpty(songIds, "List of SongIds by Genre");

        List<Song> songs = fetchSongsOrdered(songIds).stream()
                .filter(Song::isActive)
                .toList();

        MicroMethodes.validateListNotEmpty(songs, "List of active Songs by Genre");

        return SongResponseMapper.toDtoListWithId(songs);
    }

    // ED-253-SJ
    @Override
    public PlayedSongDTO playSong(Long songId, Authentication authentication) {

        UserDTO userDTO = userClient.getUserBySUB(authentication.getName());
        if (userDTO == null) {throw new ResourceNotFoundException("User", "id", authentication.getName());}

        Song song = songRepository.findByIdAndActiveTrue(songId);
        if (song == null) {throw new ResourceNotFoundException("Song", "id", authentication.getName());}

        Long currentTimesPlayed = song.getUserHistory().get(userDTO.getId());
        Long increasedTimesPlayed = currentTimesPlayed + 1;
        song.getUserHistory().put(userDTO.getId(), increasedTimesPlayed);
        songRepository.save(song);

        return new PlayedSongDTO(song.getUrl());
    }


}

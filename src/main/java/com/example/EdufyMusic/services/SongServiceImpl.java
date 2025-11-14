package com.example.EdufyMusic.services;

import com.example.EdufyMusic.clients.CreatorClient;
import com.example.EdufyMusic.clients.GenreClient;
import com.example.EdufyMusic.clients.ThumbClient;
import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.SongCreateDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.repositories.SongRepository;
import com.example.EdufyMusic.utilities.MicroMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// ED-74-SJ
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    // ED-235-SJ
    private final CreatorClient creatorClient;
    private final GenreClient genreClient;
    private final ThumbClient thumbClient;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, CreatorClient creatorClient, GenreClient genreClient, ThumbClient thumbClient)
    {
        this.songRepository = songRepository;
        this.creatorClient = creatorClient;
        this.genreClient = genreClient;
        this.thumbClient = thumbClient;
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

        List<Song> allSongsByTitle;
        List<String> roles = Roles.getRoles(authentication);

        if (roles.contains("music_admin") || roles.contains("edufy_realm_admin")) {
            allSongsByTitle = songRepository.findByTitleContainingIgnoreCase(title);
            MicroMethodes.validateListNotEmpty(allSongsByTitle, "List of Songs by title");
            return SongResponseMapper.toDtoListWithId(allSongsByTitle);
        } else {
            allSongsByTitle = songRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);
            MicroMethodes.validateListNotEmpty(allSongsByTitle, "List of Songs by title");
            return SongResponseMapper.toDtoListNoId(allSongsByTitle);
        }
    }

    // ED-80-SJ
    @Override
    public List<SongResponseDTO> getAllSongs(Authentication authentication) {

        List<Song> allSongs;
        List<String> roles = Roles.getRoles(authentication);

        if (roles.contains("music_admin") || roles.contains("edufy_realm_admin")) {
            allSongs = songRepository.findAll();
            MicroMethodes.validateListNotEmpty(allSongs, "List of all Songs");
            return SongResponseMapper.toDtoListWithId(allSongs);
        } else {
            allSongs = songRepository.findAllByActiveTrue();
            MicroMethodes.validateListNotEmpty(allSongs, "List of all Songs");
            return SongResponseMapper.toDtoListNoId(allSongs);
        }
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

    @Override
    public SongResponseDTO createSong(SongCreateDTO dto) {

        Song song = new Song();
        song.setTitle(dto.getTitle());
        song.setUrl(dto.getUrl());
        song.setLength(dto.getLength());
        song.setReleaseDate(dto.getReleaseDate());
        song.setActive(dto.isActive());

        song = songRepository.save(song);

        //TODO: Call createAlbum & connect
        //TODO: create a more solid check for null & Resource not found.

        thumbClient.createRecordOfSong(song.getId(),song.getTitle());

        if (dto.getGenreIds() == null || dto.getGenreIds().isEmpty()) {
            throw new BadRequestException("Song", "Genre Ids", dto.getGenreIds().toString());
        }
        genreClient.createRecordOfSong(song.getId(),dto.getGenreIds());

        if (dto.getCreatorIds() == null || dto.getCreatorIds().isEmpty()) {
            throw new BadRequestException("Song", "Creator Ids", dto.getCreatorIds().toString());
        }
        creatorClient.createRecordOfSong(song.getId(),dto.getCreatorIds());


        return null;
    }


}

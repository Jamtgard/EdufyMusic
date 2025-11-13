package com.example.EdufyMusic.services;

import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.repositories.SongRepository;
import com.example.EdufyMusic.utilities.MicroMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;

// ED-74-SJ
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository)
    {
        this.songRepository = songRepository;
    }

    // ED-74-SJ
    @Override
    public SongResponseDTO getSongById(long id) {
        Song song = songRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Song", "id", id)
        );

        // TODO Hämta Creator-username från List<songCreator>
        // TODO Hämta de album titlar där Song finns.

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




}

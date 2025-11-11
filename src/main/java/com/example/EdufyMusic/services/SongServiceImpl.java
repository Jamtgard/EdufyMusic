package com.example.EdufyMusic.services;

import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

// ED-74-SJ
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {this.songRepository = songRepository;}

    // ED-74-SJ
    @Override
    public SongResponseDTO getSongById(long id) {
        Song song = songRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Song", "id", id)
        );

        // TODO Hämta Creator-username från List<songCreator>
        // TODO Hämta de album titlar där Song finns.

        return SongResponseMapper.toDto(song);
    }

    // ED-49-SJ
    @Override
    public List<SongResponseDTO> findSongByTitle(String title) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_music_admin"::equals);

        List<Song> songs = isAdmin
                ? songRepository.findByTitleContainingIgnoreCase(title)
                : songRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);

        return songs.stream()
                .map(SongResponseMapper::toDto).collect(Collectors.toList());

        /*
        return songRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title)
                .stream()
                .map(SongResponseMapper::toDto)
                .toList();
         */
    }


}

package com.example.EdufyMusic.services;

import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.SongResponseMapper;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        // TODO Hämta Genre-namn från List<songGenreIds>
        // TODO Hämta Creator-username från List<songCreator>
        // TODO Hämta de album titlar där Song finns.

        return SongResponseMapper.toDto(song);
    }

    // ED-49-SJ
    @Override
    public List<SongResponseDTO> findSongByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(SongResponseMapper::toDto)
                .toList();
    }


}

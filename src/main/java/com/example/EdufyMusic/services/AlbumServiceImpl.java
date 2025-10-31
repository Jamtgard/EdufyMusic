package com.example.EdufyMusic.services;

import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ED-39-SJ
@Service
public class AlbumServiceImpl implements AlbumService {

    // ED-75-SJ

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {this.albumRepository = albumRepository;}

    @Override
    public AlbumResponseDTO getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Album", "id", id)
        );

        // TODO hämta creatorUsernames via albumCreatorIds
        // TODO hämta genreNames via AlbumGenreIds

        return AlbumResponseMapper.toDto(album);
    }

}

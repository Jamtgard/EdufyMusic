package com.example.EdufyMusic.services;

import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // ED-50-SJ
    @Override
    public List<AlbumResponseDTO> getAlbumsByTitle(String title) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        List<Album> albums = isAdmin
                ? albumRepository.findByTitleContainingIgnoreCase(title)
                : albumRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);

        return albums.stream()
                .map(AlbumResponseMapper::toDto).collect(Collectors.toList());

        /*
        return albumRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title)
                .stream()
                .map(AlbumResponseMapper::toDto)
                .toList();

         */
    }


}

package com.example.EdufyMusic.services;

import com.example.EdufyMusic.converters.Roles;
import com.example.EdufyMusic.exceptions.ResourceNotFoundException;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.mappers.AlbumResponseMapper;
import com.example.EdufyMusic.models.entities.Album;
import com.example.EdufyMusic.repositories.AlbumRepository;
import com.example.EdufyMusic.utilities.MicroMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return AlbumResponseMapper.toDtoWithId(album);
    }

    // ED-50-SJ
    // ED-80-SJ reworked structure with new way of authentication.
    @Override
    public List<AlbumResponseDTO> getAlbumsByTitle(String title, Authentication authentication) {

        List<Album> allAlbumsByTitle;
        List<String> roles = Roles.getRoles(authentication);

        if (roles.contains("music_admin") || roles.contains("edufy_realm_admin")) {
            allAlbumsByTitle = albumRepository.findByTitleContainingIgnoreCase(title);
            MicroMethodes.validateListNotEmpty(allAlbumsByTitle, "List of Albums by title");
            return AlbumResponseMapper.toDtoListWithId(allAlbumsByTitle);
        } else {
            allAlbumsByTitle = albumRepository.findByTitleContainingIgnoreCaseAndActiveIsTrue(title);
            MicroMethodes.validateListNotEmpty(allAlbumsByTitle, "List of Albums by title");
            return AlbumResponseMapper.toDtoListNoId(allAlbumsByTitle);
        }
    }

    // ED-81-SJ
    @Override
    public List<AlbumResponseDTO> getAllAlbums(Authentication authentication) {

        List<Album> allAlbums;
        List<String> roles = Roles.getRoles(authentication);

        if (roles.contains("music_admin") || roles.contains("edufy_realm_admin")) {
            allAlbums = albumRepository.findAll();
            MicroMethodes.validateListNotEmpty(allAlbums, "List of all Albums");
            return AlbumResponseMapper.toDtoListWithId(allAlbums);
        } else {
            allAlbums = albumRepository.findAllByActiveTrue();
            MicroMethodes.validateListNotEmpty(allAlbums, "List of all Albums");
            return AlbumResponseMapper.toDtoListNoId(allAlbums);
        }
    }
}

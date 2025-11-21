package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.AlbumCreateDTO;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.entities.Album;
import org.springframework.security.core.Authentication;

import java.util.List;

// ED-39-SJ
public interface AlbumService {

    // ED-75-SJ
    AlbumResponseDTO getAlbumById(Long id);

    // ED-50-SJ
    List<AlbumResponseDTO> getAlbumsByTitle(String title, Authentication authentication);

    // ED-81-SJ
    List<AlbumResponseDTO> getAllAlbums(Authentication authentication);

    // ED-237-SJ
    AlbumResponseDTO createAlbum(AlbumCreateDTO dto, boolean redirected);

    // ED-237-SJ
    Album getAlbumEntityById(Long id);


}

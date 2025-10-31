package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;

import java.util.List;

// ED-39-SJ
public interface AlbumService {

    // ED-75-SJ
    AlbumResponseDTO getAlbumById(Long id);

    // ED-50-SJ
    List<AlbumResponseDTO> getAlbumsByTitle(String title);
}

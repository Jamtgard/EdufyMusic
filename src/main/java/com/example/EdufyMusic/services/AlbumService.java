package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;

// ED-39-SJ
public interface AlbumService {

    AlbumResponseDTO getAlbumById(Long id);
}

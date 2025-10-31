package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.SongResponseDTO;

// ED-39-SJ
public interface SongService {

    // ED-74-SJ
    SongResponseDTO getSongById(long id);

}

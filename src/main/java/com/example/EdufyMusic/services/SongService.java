package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.SongResponseDTO;

import java.util.List;

// ED-39-SJ
public interface SongService {

    // ED-74-SJ
    SongResponseDTO getSongById(long id);

    //ED-49-SJ
    List<SongResponseDTO> findSongByTitle(String title);

}

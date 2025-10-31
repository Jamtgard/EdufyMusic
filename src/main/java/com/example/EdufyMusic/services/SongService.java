package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.repositories.SongRepository;

public interface SongService {

    SongResponseDTO getSongById(long id);

}

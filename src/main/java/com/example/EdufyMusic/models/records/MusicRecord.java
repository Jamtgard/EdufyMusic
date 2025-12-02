package com.example.EdufyMusic.models.records;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;

import java.util.List;

// ED-51-SJ
public record MusicRecord(
        List<AlbumResponseDTO> albums,
        List<SongResponseDTO> songs
){ }

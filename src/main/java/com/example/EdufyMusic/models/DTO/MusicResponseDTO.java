package com.example.EdufyMusic.models.DTO;

import java.util.List;

// ED-51-SJ
public record MusicResponseDTO (
        List<AlbumResponseDTO> albums,
        List<SongResponseDTO> songs
){ }

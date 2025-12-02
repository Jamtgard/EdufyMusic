package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.*;
import com.example.EdufyMusic.models.DTO.creation.AlbumCreateDTO;
import com.example.EdufyMusic.models.DTO.creation.SongCreateDTO;
import com.example.EdufyMusic.models.records.MusicRecord;
import org.springframework.security.core.Authentication;

// ED-51-SJ
public interface MusicService {

    // ED-51-SJ
    MusicRecord getDiscography(Long creatorId, Authentication authentication);

    // ED-309-SJ
    SongResponseDTO addSong(SongCreateDTO dto);
    AlbumResponseDTO addAlbum(AlbumCreateDTO dto);


}

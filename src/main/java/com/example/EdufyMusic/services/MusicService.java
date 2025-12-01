package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.*;
import org.springframework.security.core.Authentication;

// ED-51-SJ
public interface MusicService {

    // ED-51-SJ
    MusicResponseDTO getDiscography(Long creatorId, Authentication authentication);

    // ED-309-SJ
    SongResponseDTO addSong(SongCreateDTO dto);
    AlbumResponseDTO addAlbum(AlbumCreateDTO dto);


}

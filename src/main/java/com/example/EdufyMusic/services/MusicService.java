package com.example.EdufyMusic.services;

import com.example.EdufyMusic.models.DTO.MusicResponseDTO;
import org.springframework.security.core.Authentication;

// ED-51-SJ
public interface MusicService {

    // ED-51-SJ
    MusicResponseDTO getDiscography(Long creatorId, Authentication authentication);
}

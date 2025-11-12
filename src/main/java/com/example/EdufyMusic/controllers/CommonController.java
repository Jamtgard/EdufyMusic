package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// ED-80-SJ
@RestController
@RequestMapping("/music")
public class CommonController {

    private final SongService songService;

    public CommonController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs-all")
    public ResponseEntity<List<SongResponseDTO>> getAllSongs(Authentication authentication) {
        return ResponseEntity.ok(songService.getAllSongs(authentication));
    }
}

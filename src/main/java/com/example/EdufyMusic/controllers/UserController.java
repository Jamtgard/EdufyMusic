package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.PlayedSongDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.AlbumService;
import com.example.EdufyMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ED-39-SJ
@RestController
@RequestMapping("/music")
@PreAuthorize("hasRole('music_user')")
public class UserController {

    // ED-74-SJ
    private final SongService songService;
    // ED-75-SJ
    private final AlbumService albumService;

    // ED-74-75-SJ
    @Autowired
    public UserController(SongService songService, AlbumService albumService)
    {
        this.songService = songService;
        this.albumService = albumService;
    }

    // ED-49-SJ
    @GetMapping("/song/search")
    public ResponseEntity<List<SongResponseDTO>> getSongByTitle (@RequestParam String title, Authentication authentication) {
        return ResponseEntity.ok(songService.findSongByTitle(title, authentication));
    }

    // ED-50-SJ
    @GetMapping("/album/search")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumByTitle (@RequestParam String title, Authentication authentication) {
        return ResponseEntity.ok(albumService.getAlbumsByTitle(title, authentication));
    }

    // ED-253-SJ
    @GetMapping("/play/{songId}")
    public ResponseEntity<PlayedSongDTO> playSong(@PathVariable Long songId, Authentication authentication) {
        return ResponseEntity.ok(songService.playSong(songId, authentication));
    }

}

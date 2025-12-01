package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.AlbumCreateDTO;
import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.SongCreateDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.AlbumService;
import com.example.EdufyMusic.services.MusicService;
import com.example.EdufyMusic.services.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music")
@PreAuthorize("hasAnyRole('music_admin', 'edufy_realm_admin')")
public class AdminController {

    // ED-278-SJ
    private final SongService songService;
    private final AlbumService albumService;
    private final MusicService musicService;

    // ED-278-SJ
    public AdminController(SongService songService, AlbumService albumService, MusicService musicService)
    {
        this.songService = songService;
        this.albumService = albumService;
        this.musicService = musicService;
    }


    // ED-74-SJ
    @GetMapping("/song/{id}")
    public ResponseEntity<SongResponseDTO> getSongById (@PathVariable Long id){
        return ResponseEntity.ok(songService.getSongById(id));
    }

    // ED-75-SJ
    @GetMapping("/album/{id}")
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    // ED-235-SJ
    @PostMapping("/add-song")
    public ResponseEntity<SongResponseDTO> createSong(@Valid @RequestBody SongCreateDTO dto){
        return ResponseEntity.status(201).body(musicService.addSong(dto));
    }

    // ED-237-SJ
    @PostMapping("/add-album")
    public ResponseEntity<AlbumResponseDTO> createAlbum(@Valid @RequestBody AlbumCreateDTO dto){
        return ResponseEntity.status(201).body(musicService.addAlbum(dto));
    }
}

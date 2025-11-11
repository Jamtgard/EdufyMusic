package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.AlbumService;
import com.example.EdufyMusic.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/music")
public class AdminController {

    // ED-278-SJ
    private final SongService songService;
    private final AlbumService albumService;

    // ED-278-SJ
    public AdminController(SongService songService, AlbumService albumService)
    {
        this.songService = songService;
        this.albumService = albumService;
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
}

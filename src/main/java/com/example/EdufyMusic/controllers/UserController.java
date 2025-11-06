package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.AlbumService;
import com.example.EdufyMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ED-39-SJ
@RestController
@RequestMapping("/music")
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
    public ResponseEntity<List<SongResponseDTO>> getSongByTitle (@RequestParam String title) {
        return ResponseEntity.ok(songService.findSongByTitle(title));
    }

    // ED-50-SJ
    @GetMapping("/album/search")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumByTitle (@RequestParam String title) {
        return ResponseEntity.ok(albumService.getAlbumsByTitle(title));
    }

}

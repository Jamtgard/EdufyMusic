package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.AlbumResponseDTO;
import com.example.EdufyMusic.models.DTO.MusicResponseDTO;
import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.AlbumService;
import com.example.EdufyMusic.services.MusicService;
import com.example.EdufyMusic.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// ED-80-SJ
@RestController
@RequestMapping("/music")
public class CommonController {

    private final SongService songService;
    private final AlbumService albumService;
    private final MusicService musicService;

    public CommonController(SongService songService, AlbumService albumService, MusicService musicService)
    {
        this.songService = songService;
        this.albumService = albumService;
        this.musicService = musicService;
    }

    // ED-80-SJ
    @GetMapping("/songs-all")
    public ResponseEntity<List<SongResponseDTO>> getAllSongs(Authentication authentication) {
        return ResponseEntity.ok(songService.getAllSongs(authentication));
    }

    // ED-81-SJ
    @GetMapping("/albums-all")
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums(Authentication authentication) {
        return ResponseEntity.ok(albumService.getAllAlbums(authentication));
    }

    // ED-51-SJ
    @GetMapping("/discography/{creatorId}")
    public ResponseEntity<MusicResponseDTO> getDiscography(@PathVariable Long creatorId, Authentication authentication){
        return ResponseEntity.ok(musicService.getDiscography(creatorId, authentication));
    }

}

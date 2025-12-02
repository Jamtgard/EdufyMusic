package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// ED-281-SJ
@RestController
@RequestMapping("/music")
@PreAuthorize("hasRole('microservice_access')")
public class ClientController {

    private final SongService songService;

    @Autowired
    public ClientController(SongService songService) {this.songService = songService;}

    @GetMapping("/user-history/{userId}")
    public ResponseEntity<List<SongResponseDTO>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(songService.getUserHistory(userId));
    }

    // ED-273-SJ
    @GetMapping("/songs-genre/{genreId}")
    public ResponseEntity<List<SongResponseDTO>> getSongsByGenre(@PathVariable Long genreId) {
        return ResponseEntity.ok(songService.getSongsByGenre(genreId));
    }
}

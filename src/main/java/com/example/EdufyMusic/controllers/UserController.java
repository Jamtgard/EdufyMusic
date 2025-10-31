package com.example.EdufyMusic.controllers;

import com.example.EdufyMusic.models.DTO.SongResponseDTO;
import com.example.EdufyMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ED-39-SJ
@RestController
@RequestMapping("/music")
public class UserController {

    // ED-74-SJ
    private final SongService songService;

    // ED-74-SJ
    @Autowired
    public UserController(SongService songService) {this.songService = songService;}

    // ED-74-SJ
    @GetMapping("/song/{id}")
    public ResponseEntity<SongResponseDTO> getSongById (@PathVariable Long id){
        return ResponseEntity.ok(songService.getSongById(id));
    }


}

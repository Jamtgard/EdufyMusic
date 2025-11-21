package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.clients.GenreClient;
import com.example.EdufyMusic.exceptions.NoContentException;
import com.example.EdufyMusic.models.DTO.responses.GenreDTO;
import com.example.EdufyMusic.models.entities.Song;
import com.example.EdufyMusic.models.enums.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// ED-275-SJ
@Component
public class GenreResponseMapper {

    private static GenreClient genreClient;

    @Autowired
    public GenreResponseMapper(GenreClient genreClient) {GenreResponseMapper.genreClient = genreClient;}

    // ED-275-SJ
    public static List<GenreDTO> getSongGenresForAdmin (Song song) {
        List<GenreDTO> genres = genreClient.getGenresByMedia(MediaType.SONG, song.getId());
        if (genres == null || genres.isEmpty()) {
            throw new NoContentException("List of Genres");
        }
        return genres;
    }

    // ED-275-SJ
    public static List<GenreDTO> getSongGenresForUser (Song song) {
        return getSongGenresForAdmin(song).stream()
                .map(gDTO -> {
                    GenreDTO genreDTO = new GenreDTO();
                    genreDTO.setName(gDTO.getName());
                    return genreDTO;
                })
                .collect(Collectors.toList());
    }
}

package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.models.DTO.GenreDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

//ED-266-SJ - Created class to communicate with MS Genre
@Component
public class GenreClient {

    @Value("${genre.service.url}")
    private String genreServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<GenreDTO> getGenresByMedia(String mediaType, Long mediaId){
        String url = UriComponentsBuilder.fromHttpUrl(genreServiceUrl)
                .path("/api/v1/genre/by/media-id/{mediaType}/{mediaId}")
                .buildAndExpand(mediaType, mediaId)
                .toUriString();

        GenreDTO[] genres = restTemplate.getForObject(url, GenreDTO[].class);
        return genres != null ? Arrays.asList(genres) : List.of();
    }
}

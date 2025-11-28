package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.RestClientException;
import com.example.EdufyMusic.models.DTO.responses.GenreDTO;
import com.example.EdufyMusic.models.DTO.requests.GenreCreateRecordRequest;
import com.example.EdufyMusic.models.DTO.requests.SongsByGenreDTORequest;
import com.example.EdufyMusic.models.enums.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Arrays;
import java.util.List;

//ED-266-SJ - Created class to communicate with MS Genre
// ED-235-SJ Reworked
@Component
public class GenreClient {

    private final RestClient restClient;
    private final Keycloak keycloak;

    public GenreClient(RestClient.Builder builder,
                       @Value("${genre.service.url}") String genreServiceUrl,
                       Keycloak keycloak) {
        this.restClient = builder.baseUrl(genreServiceUrl).build();
        this.keycloak = keycloak;
    }

    public List<GenreDTO> getGenresByMedia(MediaType mediaType, Long mediaId){
        try {
            ResponseEntity<GenreDTO[]> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/by/media-id/{mediaType}/{mediaId}")
                            .build(mediaType, mediaId))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloak.getAccessToken())
                    .retrieve()
                    .toEntity(GenreDTO[].class);

            GenreDTO[] genres = response.getBody();
            return genres != null ? Arrays.asList(genres) : List.of();

        } catch (RestClientResponseException e) {
            throw new RestClientException("EdufyMusic", "EdufyGenre");
        }
    }



    // ED-235-SJ
    public boolean createRecordOfSong(Long mediaId, List<Long> genreIds){
        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri("/media/record")
                    .body(new GenreCreateRecordRequest(mediaId, MediaType.SONG, genreIds))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloak.getAccessToken())
                    .retrieve()
                    .toBodilessEntity();

            return response.getStatusCode().is2xxSuccessful();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("Genre Service Error: " + errorMessage);

        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy Genre");
        }
    }

    // ED-273-SJ
    public SongsByGenreDTORequest getSongsByGenre(Long genreId, MediaType mediaType){
        try {
            ResponseEntity<SongsByGenreDTORequest> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/{genreId}/media/by-type/{mediaType}")
                            .build(genreId, mediaType))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloak.getAccessToken())
                    .retrieve()
                    .toEntity(SongsByGenreDTORequest.class);

            return response.getBody();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("Genre Service Error: " + errorMessage);
        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy Genre");
        }
    }
}
